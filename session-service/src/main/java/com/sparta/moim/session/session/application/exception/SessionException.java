package com.sparta.moim.session.session.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.session.session.domain.error.code.SessionCode;

public class SessionException extends BaseException {
  /**
   * 주어진 세션 오류 코드를 기반으로 세션 예외를 생성합니다.
   *
   * <p>입력된 {@code SessionCode}를 {@code BaseException}의 생성자로 전달하여 예외를 초기화합니다.</p>
   *
   * @param code 세션 관련 오류 코드를 나타내는 값
   */
  public SessionException(SessionCode code) {
    super(code);
  }
}
