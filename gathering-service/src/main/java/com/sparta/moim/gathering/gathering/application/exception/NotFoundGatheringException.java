package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class NotFoundGatheringException extends GatheringException {
  public NotFoundGatheringException() {
    super(GatheringCode.NOT_FOUND_GATHERING);
  }
}
