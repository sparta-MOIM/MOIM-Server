package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class AleardyParticipateFoundGatheringException extends GatheringException {
  public AleardyParticipateFoundGatheringException() {
    super(GatheringCode.ALREADY_PARTICIPATE_GATHERING);
  }
}
