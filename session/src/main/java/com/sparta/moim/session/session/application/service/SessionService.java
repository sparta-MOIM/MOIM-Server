package com.sparta.moim.session.session.application.service;

import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

  public void updateSession() {
  }

  public void statusUpdateSession() {

  }

  public void deleteSession() {
  }

  public void applySession() {

  }
}
