package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class GatheringException extends BaseException {
  public GatheringException(GatheringCode code) {
    super(code);
  }
}
