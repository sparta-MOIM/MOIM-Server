package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import com.sparta.moim.gathering.shared.enums.EventType;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class MemberRedisProxyService implements MemberService {
  @Value("${spring.data.redis.stream-join-key}")
  private String streamJoinKey;

  @Value("${spring.data.redis.stream-leave-key}")
  private String streamLeaveKey;

  private final RedisTemplate<String, Member> redisTemplate;
  private final MemberServiceStruct memberServiceStruct;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  @Override
  public void joinGathering(JoinGatheringCommand command) {
    Member member = command.toDomain();
    memberServiceStruct.joinGathering(command);
//    redisTemplate.opsForStream().add(streamJoinKey, member.toMap());
    try {
      String payload = objectMapper.writeValueAsString(member.toMap());
      outboxRepository.save(OutboxEvent.builder()
          .streamKey(streamJoinKey)
          .eventType(EventType.MEMBER_JOINED)
          .payload(payload)
          .status(OutboxType.PENDING)
          .build());
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize member data for outbox event", e);
    }

  }

  @Override
  public void leaveGathering(LeaveGatheringCommand command) {
    Map<String, String> map = Map.of(
        "gathering_id", command.gatheringId().toString(),
        "member_name", command.username()
    );

    memberServiceStruct.leaveGathering(command);
    redisTemplate.opsForStream().add(streamLeaveKey, map);
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
