package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class NotJoinGatheringException extends GatheringException {
  public NotJoinGatheringException() {
    super(GatheringCode.NOT_JOIN_GATHERING);
  }
}
