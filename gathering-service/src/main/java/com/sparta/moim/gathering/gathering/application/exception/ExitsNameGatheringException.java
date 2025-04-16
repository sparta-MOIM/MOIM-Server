package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class ExitsNameGatheringException extends GatheringException {
  public ExitsNameGatheringException() {
    super(GatheringCode.EXISTS_NAME_GATHERING);
  }
}
