package com.sparta.moim.session.session.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.session.session.application.dto.DeleteSessionCommand;
import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.SearchSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateStateStateCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import com.sparta.moim.session.session.domain.repository.SessionCustomRepository;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;
  private final SessionCustomRepository sessionCustomRepository;

  public CreateSessionResult createSession(CreateSessionCommand command) {
    //TODO 이름은 중복이 될 수 없습니다.
    //TODO reason은 공백이 될 수 없습니다. // 관리자가 생성하면 자동으로 생성되어집니다.
    return CreateSessionResult.create(sessionRepository.save(command.toDomain()));
  }

  @Transactional(readOnly = true)
  public GetSessionResult getSession(UUID sessionId) {
    return GetSessionResult.get(sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("Session not found")));
  }

  @Transactional(readOnly = true)
  public SearchSessionResult searchSession(SearchSessionCommand command) {
    Pagination<Session> sessions = sessionCustomRepository.searchSession(command.toCriteria());
    return SearchSessionResult.search(sessions.getContent(),
        sessions.getTotal(),
        sessions.getPage(),
        sessions.getSize());
  }

  @Transactional
  public void updateSession(UpdateSessionCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.update(command.toDomain());
  }

  @Transactional
  public void statusUpdateSession(UpdateStateStateCommand command) {
    //TODO READY인 상태에서는 변경이 불가합니다.
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.stateChange(SessionStatus.valueOf(command.status()));
  }

  @Transactional
  public void deleteSession(DeleteSessionCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.softDelete(command.username());
  }

  @Transactional
  public void applySession(UUID sessionId) {
    //TODO 레디인 상태에서만 승인을 할 수 가 있다.
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    session.confirm();

  }
}
