package com.moim.post.application.exception.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostExceptionCode implements Code {

  NOT_FOUND_FEED(HttpStatus.NOT_FOUND, "P001", "피드을 찾을 수 없습니다."),
  NOT_FOUND_VOTE(HttpStatus.NOT_FOUND, "P002", "투표을 찾을 수 없습니다."),
  UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "P003", "해당 요청에 대한 권한이 없습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  PostExceptionCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}