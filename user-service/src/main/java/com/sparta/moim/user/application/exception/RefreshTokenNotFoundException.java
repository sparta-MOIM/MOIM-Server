package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class RefreshTokenNotFoundException extends BaseException {

  public RefreshTokenNotFoundException(Code code) {
    super(code);
  }
}
