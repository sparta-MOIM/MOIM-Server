package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.sparta.moim.gathering.gathering.application.dto.command.event.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRdsProxyService implements MemberService {
  private final MemberServiceStruct memberServiceStruct;
  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public void joinGathering(JoinGatheringCommand command) {
    memberServiceStruct.joinGathering(command);
    memberRepository.save(command.toDomain());
  }

  @Override
  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    memberServiceStruct.leaveGathering(command);
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId(), command.userId());
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
