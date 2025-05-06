package com.sparta.moim.session.shared.error.exception;

import com.sparta.moim.session.shared.error.code.SessionCode;

public class SessionNotJoinException extends SessionException {
  public SessionNotJoinException() {
    super(SessionCode.SESSION_NOT_JOINED);
  }
}
