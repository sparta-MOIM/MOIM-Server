package com.sparta.moim.session.member.infrastructure.event.listener;

import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import com.sparta.moim.session.shared.dto.SharedSessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveMember {
  private final MemberRepository memberRepository;

  @EventListener
  public void save(SharedSessionMember sharedSessionMember) {
    log.info("Saving publisher {}", sharedSessionMember.getMemberName());
    memberRepository.save(Member.from(sharedSessionMember));
  }
}
