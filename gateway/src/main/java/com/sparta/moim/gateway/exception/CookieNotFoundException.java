package com.sparta.moim.gateway.exception;

import com.sparta.moim.common.exception.BaseException;

public class CookieNotFoundException extends BaseException {
  public CookieNotFoundException(GatewayErrorCode errorCode) {
    super(errorCode);
  }
}
