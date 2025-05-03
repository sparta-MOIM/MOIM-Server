package com.sparta.moim.session.session.application.service;

import com.sparta.moim.session.session.domain.repository.SessionRepository;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionValidationService {
  private final SessionRepository sessionRepository;


  public void isValidateSession(UUID sessionId) {
    sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));

  }

  public void isValidateSessionTimeCheck(UUID sessionId) {
    boolean isCollectJoinSession = sessionRepository.checkOpenTimeByTrackingId(sessionId, LocalDateTime.now())
        .isPresent();

    if (!isCollectJoinSession) {
      throw new SessionException(SessionCode.TIME_OUT_SESSION);
    }
  }

  public void isValidateSessionStatus(UUID sessionId) {
    if (sessionRepository.checkSessionIdAndStatusOpen(sessionId).isPresent()) {
      throw new SessionException(SessionCode.NOT_OPEN_SESSION);
    }
  }


}
