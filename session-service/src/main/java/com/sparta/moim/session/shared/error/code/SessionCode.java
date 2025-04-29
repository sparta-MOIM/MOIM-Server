package com.sparta.moim.session.shared.error.code;

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
  TIME_OUT_SESSION(HttpStatus.BAD_REQUEST, "S005", "현재시간으로 세션에 참여하실 수 없습니다."),
  ALREADY_PARTICIPATE_SESSION(HttpStatus.BAD_REQUEST, "S006", "this session is already participate"),
  NOT_OPEN_SESSION(HttpStatus.BAD_REQUEST, "S007", "this session is not open"),
  NOT_CONNECTED_SESSION(HttpStatus.BAD_REQUEST, "S008", "this session is not connected"),
  ROLE_NOT_ALLOWED_SESSION(HttpStatus.BAD_REQUEST,"S009", "You are not allowed to gather this role"),

  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
