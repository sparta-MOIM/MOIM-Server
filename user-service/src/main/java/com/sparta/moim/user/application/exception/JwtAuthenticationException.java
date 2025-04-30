package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.exception.BaseException;

public class JwtAuthenticationException extends BaseException {
  public JwtAuthenticationException(AuthErrorCode errorCode) {
    super(errorCode);
  }
}
