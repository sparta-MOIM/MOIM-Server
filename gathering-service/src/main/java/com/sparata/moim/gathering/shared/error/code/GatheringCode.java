package com.sparata.moim.gathering.shared.error.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatheringCode implements Code {

  NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "G001", "Gathering not found"),
  EXITS_TITLE_GATHERING(HttpStatus.BAD_REQUEST, "G002", "Gathering title is exits"),
  STATUS_READY_GATHERING(HttpStatus.BAD_REQUEST, "G003", "Gathering status is ready"),
  STATUS_NOT_READY_GATHERING(HttpStatus.BAD_REQUEST, "G004", "Gathering status is not ready"),
  TIME_OUT_GATHERING(HttpStatus.BAD_REQUEST, "G005", "현재시간으로 세션에 참여하실 수 없습니다."),
  ALREADY_PARTICIPATE_GATHERING(HttpStatus.BAD_REQUEST, "G006", "this Gathering is already participate"),
  NOT_OPEN_GATHERING(HttpStatus.BAD_REQUEST, "G007", "this Gathering is not open"),

  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
