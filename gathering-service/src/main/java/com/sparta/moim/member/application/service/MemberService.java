package com.sparta.moim.member.application.service;

import com.sparta.moim.member.application.dto.command.JoinGatheringCommand;
import com.sparta.moim.member.application.dto.command.LeaveGatheringCommand;
import com.sparta.moim.member.domain.Member;
import com.sparta.moim.member.domain.repository.MemberRepository;
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

  public void removeGathering() {

  }
}
