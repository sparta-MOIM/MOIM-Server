package com.sparata.moim.gathering.shared.error.exception;

import com.sparata.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.common.exception.BaseException;

public class GatheringException extends BaseException {
  public GatheringException(GatheringCode code) {
    super(code);
  }
}
