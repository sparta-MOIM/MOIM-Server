package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class MemberRedisProxyService implements MemberService {
  @Value("${spring.data.redis.stream-key}")
  private String streamKey;
  private final RedisTemplate<String, Member> redisTemplate;
  private final MemberServiceStruct memberServiceStruct;

  @Override
  public void joinGathering(JoinGatheringCommand command) {
    Member member = command.toDomain();
    memberServiceStruct.joinGathering(command);
    redisTemplate.opsForStream().add(streamKey, member.toMap());
  }

  @Override
  public void leaveGathering(LeaveGatheringCommand command) {
    memberServiceStruct.leaveGathering(command);
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
