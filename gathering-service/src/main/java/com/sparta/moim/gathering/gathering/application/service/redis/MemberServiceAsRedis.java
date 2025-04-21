package com.sparta.moim.gathering.gathering.application.service.redis;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.exception.AlreadyParticipateFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotOpenGatheringException;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class MemberServiceAsRedis implements MemberService {
  private final RedisTemplate<String, Member> redisTemplate;
  private final MemberRepository memberRepository;
  private final GatheringValidationRepository gatheringValidationRepository;

  public void joinGathering(JoinGatheringCommand command) {
//    validateGatheringExists(command.gatheringId());
//    statusTrueValidate(command);

//    if (memberRepository.existsByMemberId(command.username())) {
//      throw new AlreadyParticipateFoundGatheringException();
//    }

    Member member = command.toDomain();
    // 대기열을 통해 메시지를 전달한다.
    redisTemplate.opsForStream().add("stream:gathering_join", member.toMap());
  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    if (!gatheringValidationRepository.isGatheringOpen(command.gatheringId())) {
      throw new NotOpenGatheringException();
    }
  }

  //미구현
  public void leaveGathering(LeaveGatheringCommand command) {

  }

  //미구현
  public void removeGathering(RemoveGatheringCommand command) {

  }

  private void validateGatheringExists(UUID id) {
    gatheringValidationRepository.existsByTrackingIdAndDeletedAtNull(id);
  }
}
