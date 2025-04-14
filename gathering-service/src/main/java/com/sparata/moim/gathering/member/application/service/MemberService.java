package com.sparata.moim.gathering.member.application.service;

import com.sparata.moim.gathering.member.application.dto.command.JoinGatheringCommand;
import com.sparata.moim.gathering.member.application.dto.command.LeaveGatheringCommand;
import com.sparata.moim.gathering.member.application.dto.command.RemoveGatheringCommand;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public void joinGathering(JoinGatheringCommand command) {
    memberRepository.save(command.toDomain());
  }

  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId().toString(), command.username());
  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId().toString(), command.users());
  }
}
