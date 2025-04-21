package com.sparta.moim.gateway.exception;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatewayErrorCode implements Code {
  ACCESS_TOKEN_COOKIE_NOT_FOUND(HttpStatus.BAD_REQUEST, "GW001", "엑세스 토큰 쿠키가 없습니다."),
  ACCESS_TOKEN_COOKIE_IS_EMPTY(HttpStatus.BAD_REQUEST, "GW002", "엑세스 토큰 쿠키 값이 빈 값입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
