package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements Code {
  ALREADY_EXISTS_USERNAME(HttpStatus.CONFLICT, "U001", "이미 사용 중인 username 입니다."),
  LOGIN_FAILED(HttpStatus.BAD_REQUEST, "U002", "아이디 혹은 비밀번호를 확인해주세요."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
