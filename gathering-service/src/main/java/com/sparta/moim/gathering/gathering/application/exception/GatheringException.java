package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;
import com.sparta.moim.common.exception.BaseException;

public class GatheringException extends BaseException {
  public GatheringException(GatheringCode code) {
    super(code);
  }
}
