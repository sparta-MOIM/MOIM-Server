package com.sparta.moim.user.application.exception;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements Code {
  ACCESS_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "A001", "엑세스 토큰이 없습니다."),
  ACCESS_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "A002", "엑세스 토큰이 빈 값입니다."),
  MALFORMED_JWT(HttpStatus.BAD_REQUEST, "A005", "잘못된 JWT 토큰입니다."),
  SIGNATURE_NOT_VALID(HttpStatus.BAD_REQUEST, "A006", "JWT 토큰 서명이 올바르지 않습니다."),
  JWT_NOT_VALID(HttpStatus.BAD_REQUEST, "A007", "JWT 토큰이 유효하지 않습니다."),

  EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "A003", "JWT 토큰이 만료되었습니다."),
  UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "A004", "지원되지 않는 JWT 토큰입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
