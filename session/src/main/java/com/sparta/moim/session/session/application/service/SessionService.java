package com.sparta.moim.session.session.application.service;

import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateStateStateCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;

  public CreateSessionResult createSession(CreateSessionCommand command) {
    //TODO 이름은 중복이 될 수 없습니다.
    //TODO reason은 공백이 될 수 없습니다. // 관리자가 생성하면 자동으로 생성되어집니다.
    return CreateSessionResult.create(sessionRepository.save(command.toDomain()));
  }

  @Transactional(readOnly = true)
  public GetSessionResult getSession(UUID sessionId) {
    return GetSessionResult.get(sessionRepository.findByTrackingId(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("Session not found")));
  }

  public void searchSession() {

  }

  @Transactional
  public void updateSession(UpdateSessionCommand command) {
    Session session = sessionRepository.findByTrackingId(command.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.update(command.toDomain());
  }

  @Transactional
  public void statusUpdateSession(UpdateStateStateCommand command) {
    //TODO READY인 상태에서는 변경이 불가합니다.
    Session session = sessionRepository.findByTrackingId(command.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.stateChange(SessionStatus.valueOf(command.status()));
  }

  public void deleteSession() {
  }

  public void applySession() {

  }
}
