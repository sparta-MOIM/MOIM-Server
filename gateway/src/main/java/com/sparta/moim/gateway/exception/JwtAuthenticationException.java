package com.sparta.moim.gateway.exception;

import com.sparta.moim.common.exception.BaseException;

public class JwtAuthenticationException extends BaseException {
  public JwtAuthenticationException(GatewayErrorCode errorCode) {
    super(errorCode);
  }
}
