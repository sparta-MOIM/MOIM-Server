package com.sparta.moim.session.member.application;

import com.sparta.moim.session.member.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  @Transactional
  public void joinMember(UUID sessionId) {

  }

  @Transactional
  public void leaveMember(UUID sessionId) {

  }

  @Transactional
  public void removeMember(UUID sessionId) {

  }
}
