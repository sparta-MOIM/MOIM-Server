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
public class SaveMemberListener {
  private final MemberRepository memberRepository;

  @EventListener
  public void save(SharedSessionMember sharedSessionMember) {
    try {
      log.info("Saving publisher {}", sharedSessionMember.memberId());
      memberRepository.save(Member.from(sharedSessionMember));
      log.info("Successfully saved publisher {}", sharedSessionMember.memberId());
    } catch (Exception e) {
      log.error("Failed to save publisher {}: {}", sharedSessionMember.memberId(), e.getMessage(), e);
      //TODO 보상 트랜잭션 적용
    }
  }
}
