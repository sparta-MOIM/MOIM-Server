package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
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

  @Override
  public void joinGathering(JoinGatheringCommand command) {
    Member member = command.toDomain();
    memberServiceStruct.joinGathering(command);
    redisTemplate.opsForStream().add(streamJoinKey, member.toMap());
  }

  @Override
  public void leaveGathering(LeaveGatheringCommand command) {
    Map<String, String> map = new HashMap<>(2);
    map.put("gathering_id", command.gatheringId().toString());
    map.put("member_name", command.username());

    memberServiceStruct.leaveGathering(command);
    redisTemplate.opsForStream().add(streamLeaveKey, map);
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
