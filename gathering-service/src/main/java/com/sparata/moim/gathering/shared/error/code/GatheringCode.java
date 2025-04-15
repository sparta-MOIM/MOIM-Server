package com.sparata.moim.gathering.shared.error.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatheringCode implements Code {

  NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "G001", "Gathering not found"),
  EXITS_NAME_GATHERING(HttpStatus.BAD_REQUEST, "G002", "Gathering name is exits"),
  TIME_OUT_GATHERING(HttpStatus.BAD_REQUEST, "G003", "현재시간으로 세션에 참여하실 수 없습니다."),
  ALREADY_PARTICIPATE_GATHERING(HttpStatus.BAD_REQUEST, "G004", "this Gathering is already participate"),
  NOT_OPEN_GATHERING(HttpStatus.BAD_REQUEST, "G005", "this Gathering is not open"),
  NOT_ALLOW_ROLE_GATHERING(HttpStatus.BAD_REQUEST, "G006", "the authority gathering is not allowed"),

  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
