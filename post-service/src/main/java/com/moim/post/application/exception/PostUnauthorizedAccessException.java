package com.moim.post.application.exception;

import com.moim.post.application.exception.code.PostExceptionCode;
import com.sparta.moim.common.exception.BaseException;

public class PostUnauthorizedAccessException extends BaseException {
  public PostUnauthorizedAccessException() {
    super(PostExceptionCode.UNAUTHORIZED_REQUEST);
  }
}
