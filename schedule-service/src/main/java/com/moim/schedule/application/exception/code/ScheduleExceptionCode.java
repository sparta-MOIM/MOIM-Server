package com.moim.schedule.application.exception.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ScheduleExceptionCode implements Code {

  NOT_FOUND_SCHEDULE(HttpStatus.BAD_REQUEST, "SCG001" , "일정을 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ScheduleExceptionCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}