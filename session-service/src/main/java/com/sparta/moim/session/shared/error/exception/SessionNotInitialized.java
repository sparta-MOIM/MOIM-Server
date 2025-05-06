package com.sparta.moim.session.shared.error.exception;

import com.sparta.moim.session.shared.error.code.SessionCode;

public class SessionNotInitialized  extends SessionException {
  public SessionNotInitialized() {
    super(SessionCode.SESSION_NOT_INITIALIZED);
  }
}
