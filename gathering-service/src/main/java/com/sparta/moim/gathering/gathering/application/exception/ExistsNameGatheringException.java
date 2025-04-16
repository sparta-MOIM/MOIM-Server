package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class ExistsNameGatheringException extends GatheringException {
  public ExistsNameGatheringException() {
    super(GatheringCode.EXISTS_NAME_GATHERING);
  }
}
