package com.sparta.moim.session.member.infrastructure.event.listener;

import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import com.sparta.moim.session.shared.dto.SharedRemoveSession;
import com.sparta.moim.session.shared.dto.SharedSessionMember;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveSessionListener {


  private final MemberRepository memberRepository;

  @EventListener
  public void remove(SharedRemoveSession sharedRemoveSession) {
    UUID sessionId = sharedRemoveSession.sessionId();
    try {
      log.info("Starting to delete members for session {}", sessionId);
      memberRepository.deleteAllBySessionId(sessionId);
      log.info("Successfully deleted all members for session {}", sessionId);
    } catch (Exception e) {
      log.error("Error while deleting members for session {}", sessionId, e);
      //TODO 보상 트랜잭션 적용
    }
  }
}
