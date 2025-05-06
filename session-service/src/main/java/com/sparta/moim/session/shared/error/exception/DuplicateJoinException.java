package com.sparta.moim.session.shared.error.exception;

import com.sparta.moim.session.shared.error.code.SessionCode;

public class DuplicateJoinException extends SessionException {
  public DuplicateJoinException() {
    super(SessionCode.ALREADY_PARTICIPATE_SESSION);
  }
}
