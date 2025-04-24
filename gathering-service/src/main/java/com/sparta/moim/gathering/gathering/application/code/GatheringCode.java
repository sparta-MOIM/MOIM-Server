package com.sparta.moim.gathering.gathering.application.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatheringCode implements Code {

  NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "G001", "The requested gathering could not be found"),
  EXISTS_NAME_GATHERING(HttpStatus.BAD_REQUEST, "G002", "A gathering with this title already exists"),
  ALREADY_PARTICIPATE_GATHERING(HttpStatus.BAD_REQUEST, "G003", "You are already participating in this gathering"),
  NOT_OPEN_GATHERING(HttpStatus.BAD_REQUEST, "G004", "This gathering is not open"),
  ROLE_NOT_ALLOWED_GATHERING(HttpStatus.BAD_REQUEST,"G005", "You are not allowed to gather this role"),
  NOT_FOUND_GATHERING_MEMBER(HttpStatus.NOT_FOUND,"G006", "The gathering member could not be found"),
  PROCESSING_LEAVE_GATHERING_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR,"G007", "An error occurred while processing the gathering leave request"),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

}
