package com.sparta.moim.gathering.shared.error.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatheringCode implements Code {

  NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "G001", "Gathering not found"),
  EXISTS_NAME_GATHERING(HttpStatus.BAD_REQUEST, "G002", "Gathering title already exists"),
  TIME_OUT_GATHERING(HttpStatus.BAD_REQUEST, "G003", "You cannot join the session at the current time"),
  ALREADY_PARTICIPATE_GATHERING(HttpStatus.BAD_REQUEST, "G004", "You are already participating in this gathering"),
  NOT_OPEN_GATHERING(HttpStatus.BAD_REQUEST, "G005", "This gathering is not open"),
  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
