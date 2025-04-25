package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class NotConnectedGatheringException extends GatheringException {
  public NotConnectedGatheringException() {
    super(GatheringCode.NOT_CONNECTED_INFRA);
  }
}
