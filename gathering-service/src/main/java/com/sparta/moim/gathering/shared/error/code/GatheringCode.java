package com.sparta.moim.gathering.shared.error.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatheringCode implements Code {

  NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "G001", "The requested gathering could not be found"),
  EXISTS_NAME_GATHERING(HttpStatus.BAD_REQUEST, "G002", "A gathering with this title already exists"),
  TIME_OUT_GATHERING(HttpStatus.BAD_REQUEST, "G003", "The time window to join this gathering has expired"),
  ALREADY_PARTICIPATE_GATHERING(HttpStatus.BAD_REQUEST, "G004", "You are already participating in this gathering"),
  NOT_OPEN_GATHERING(HttpStatus.BAD_REQUEST, "G005", "This gathering is not open"),
  ROLE_NOT_ALLOWED_GATHERING(HttpStatus.BAD_REQUEST,"G006", "You are not allowed to gather this role"),
  NOT_FOUND_GATHERING_MEMBER(HttpStatus.NOT_FOUND,"G007", "The gathering member could not be found"),
  ;


  private final HttpStatus status;
  private final String code;
  private final String message;


}
