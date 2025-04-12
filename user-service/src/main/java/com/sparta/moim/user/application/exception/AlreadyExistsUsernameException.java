package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class AlreadyExistsUsernameException extends BaseException {

  public AlreadyExistsUsernameException(Code code) {
    super(code);
  }
}
