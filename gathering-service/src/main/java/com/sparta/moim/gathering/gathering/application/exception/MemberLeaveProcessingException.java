package com.sparta.moim.gathering.gathering.application.exception;

import static com.sparta.moim.gathering.gathering.application.code.GatheringCode.PROCESSING_LEAVE_GATHERING_MEMBER;

public class MemberLeaveProcessingException extends GatheringException {
  public MemberLeaveProcessingException() {
    super(PROCESSING_LEAVE_GATHERING_MEMBER);
  }
}
