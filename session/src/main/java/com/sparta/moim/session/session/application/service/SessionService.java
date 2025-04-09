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
    //TODO 이름은 중복이 될 수 없습니다.
    //TODO reason은 공백이 될 수 없습니다. // 관리자가 생성하면 자동으로 생성되어집니다.
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
