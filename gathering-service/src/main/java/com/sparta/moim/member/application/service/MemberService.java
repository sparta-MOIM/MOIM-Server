package com.sparta.moim.member.application.service;

import com.sparta.moim.member.application.dto.command.JoinGatheringCommand;
import com.sparta.moim.member.domain.Member;
import com.sparta.moim.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public void joinGathering(JoinGatheringCommand command) {
    memberRepository.save(command.toDomain());
  }

  public void leaveGathering() {

  }

  public void removeGathering() {

  }
}
