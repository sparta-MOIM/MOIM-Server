package com.sparta.moim.gathering.shared.error.exception;

import com.sparta.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.common.exception.BaseException;

public class GatheringException extends BaseException {
  /**
   * Gathering 도메인에서 발생하는 예외를 생성합니다.
   *
   * @param code 발생한 예외의 상세 원인을 나타내는 GatheringCode
   */
  public GatheringException(GatheringCode code) {
    super(code);
  }
}
