package com.sparta.moim.session.session.infrastructure.event.listener;

import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import com.sparta.moim.session.shared.dto.SharedDecreaseMember;
import com.sparta.moim.session.shared.dto.SharedIncreaseMember;
import com.sparta.moim.session.shared.dto.SharedRemoveMember;
import com.sparta.moim.session.shared.dto.SharedRemoveSession;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandleMemberCountListener {
  private final SessionRepository sessionRepository;

  @EventListener
  @Transactional
  public void increase(SharedIncreaseMember increaseMember) {
    UUID sessionId = increaseMember.sessionId();
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    session.increase();
  }

  @EventListener
  @Transactional
  public void decrease(SharedDecreaseMember decreaseMember) {
    UUID sessionId = decreaseMember.sessionId();
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    session.decrease();
  }

  @EventListener
  @Transactional
  public void remove(SharedRemoveMember removeMember) {
    UUID sessionId = removeMember.sessionId();
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    session.remove(removeMember.count());
  }
}
