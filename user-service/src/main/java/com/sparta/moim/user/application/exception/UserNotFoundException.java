package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class UserNotFoundException extends BaseException {

  public UserNotFoundException(Code code) {
    super(code);
  }
}
