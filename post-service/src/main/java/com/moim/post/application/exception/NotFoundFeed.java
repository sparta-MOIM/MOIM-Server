package com.moim.post.application.exception;

import com.moim.post.application.exception.code.PostExceptionCode;
import com.sparta.moim.common.exception.BaseException;

public class NotFoundFeed extends BaseException {
  public NotFoundFeed() {
    super(PostExceptionCode.NOT_FOUND_FEED);
  }
}
