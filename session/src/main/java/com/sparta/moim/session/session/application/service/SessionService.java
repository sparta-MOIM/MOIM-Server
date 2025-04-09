package com.sparta.moim.session.session.application.service;

import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;

  public CreateSessionResult createSession(CreateSessionCommand command) {
    return CreateSessionResult.create(sessionRepository.save(command.toDomain()));
  }

  public void getSession() {

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
