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
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U003", "존재하지 않는 회원입니다."),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "U004", "리프래시 토큰을 찾을 수 없습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
