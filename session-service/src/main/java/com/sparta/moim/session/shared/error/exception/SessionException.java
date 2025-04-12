package com.sparta.moim.session.shared.error.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.session.shared.error.code.SessionCode;

public class SessionException extends BaseException {
  public SessionException(SessionCode code) {
    super(code);
  }
}
