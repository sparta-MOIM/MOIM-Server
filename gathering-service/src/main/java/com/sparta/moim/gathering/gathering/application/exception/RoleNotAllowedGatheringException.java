package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class RoleNotAllowedGatheringException extends GatheringException {
  public RoleNotAllowedGatheringException() {
    super(GatheringCode.ROLE_NOT_ALLOWED_GATHERING);
  }
}
