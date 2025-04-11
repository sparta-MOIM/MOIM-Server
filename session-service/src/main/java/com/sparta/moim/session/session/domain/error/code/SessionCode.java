package com.sparta.moim.session.session.domain.error.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SessionCode implements Code {

  NOT_FOUND_SESSION(HttpStatus.NOT_FOUND, "S001", "Session not found"),
  EXITS_TITLE_SESSION(HttpStatus.BAD_REQUEST, "S002", "Session title is exits"),
  STATUS_READY_SESSION(HttpStatus.BAD_REQUEST, "S003", "Session status is ready"),
  STATUS_NOT_READY_SESSION(HttpStatus.BAD_REQUEST, "S004", "Session status is not ready"),

  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
