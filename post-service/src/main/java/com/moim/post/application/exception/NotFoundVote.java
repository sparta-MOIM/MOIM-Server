package com.moim.post.application.exception;

import com.moim.post.application.exception.code.PostExceptionCode;
import com.sparta.moim.common.exception.BaseException;

public class NotFoundVote extends BaseException {
  public NotFoundVote() {
    super(PostExceptionCode.NOT_FOUND_VOTE);
  }
}
