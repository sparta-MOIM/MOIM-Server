package com.sparta.moim.session.shared.error.exception;

import com.sparta.moim.session.shared.error.code.SessionCode;

public class SessionFullException extends SessionException {
  public SessionFullException() {
    super(SessionCode.SESSION_FULL_EXCEPTION);
  }
}
