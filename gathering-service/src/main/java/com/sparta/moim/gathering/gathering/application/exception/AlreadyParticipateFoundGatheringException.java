package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class AlreadyParticipateFoundGatheringException extends GatheringException {
  public AlreadyParticipateFoundGatheringException() {
    super(GatheringCode.ALREADY_PARTICIPATE_GATHERING);
  }
}
