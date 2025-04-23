package com.sparta.moim.gathering.gathering.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.gathering.gathering.application.code.GatheringCode;

public class NotFoundGatheringMemberException extends BaseException {
  public NotFoundGatheringMemberException() {
    super(GatheringCode.NOT_FOUND_GATHERING_MEMBER);
  }
}
