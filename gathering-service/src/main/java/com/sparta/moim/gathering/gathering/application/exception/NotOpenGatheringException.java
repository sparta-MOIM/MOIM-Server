package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class NotOpenGatheringException extends GatheringException {
  public NotOpenGatheringException() {
    super(GatheringCode.NOT_OPEN_GATHERING);
  }
}
