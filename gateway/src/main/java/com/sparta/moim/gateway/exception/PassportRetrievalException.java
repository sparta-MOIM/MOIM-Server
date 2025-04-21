package com.sparta.moim.gateway.exception;

import com.sparta.moim.common.exception.BaseException;

public class PassportRetrievalException extends BaseException {
  public PassportRetrievalException(GatewayErrorCode errorCode) {
    super(errorCode);
  }
}

