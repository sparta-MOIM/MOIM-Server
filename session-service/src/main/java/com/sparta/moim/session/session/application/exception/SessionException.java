package com.sparta.moim.session.session.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.session.session.domain.error.code.SessionCode;

public class SessionException extends BaseException {
  public SessionException(SessionCode code) {
    super(code);
  }
}
