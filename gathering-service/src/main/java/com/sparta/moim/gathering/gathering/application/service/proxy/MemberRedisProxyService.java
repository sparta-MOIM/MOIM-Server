package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.gathering.gathering.application.dto.command.event.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import com.sparta.moim.gathering.shared.enums.EventType;
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
    //TODO 전략 패턴으로 분리 및 아웃박스 패턴 vs 직접 Redis 접근 성능 비교를 위해 주석 처리
//    redisTemplate.opsForStream().add(streamJoinKey, member.toMap());
    try {
      String payload = objectMapper.writeValueAsString(member.toMap());
      outboxRepository.save(OutboxEvent.create(command.toEventCriteria(streamJoinKey, EventType.MEMBER_JOINED, payload)));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize member data for outbox event", e);
    }

  }

  @Override
  public void leaveGathering(LeaveGatheringCommand command) {
    Map<String, String> map = Map.of(
        "gathering_id", command.gatheringId().toString(),
        "member_id", command.userId().toString()
    );

    memberServiceStruct.leaveGathering(command);
    try {
      String payload = objectMapper.writeValueAsString(map);
//    redisTemplate.opsForStream().add(streamLeaveKey, map);
      outboxRepository.save(OutboxEvent.create(command.toEventCriteria(streamLeaveKey, EventType.MEMBER_LEAVE, payload)));

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize member data for outbox event", e);
    }
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
