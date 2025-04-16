package com.sparta.moim.chat.infrastructure.response;


import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatCode implements Code {
  CHAT_FOUND(HttpStatus.OK, "CT200", "채팅을 성공적으로 조회하였습니다."),
  CHAT_UPDATE(HttpStatus.OK, "CT201", "채팅을 성공적으로 수정하였습니다."),
  CHAT_DELETE(HttpStatus.OK, "CT202", "채팅을 성공적으로 삭제하였습니다."),
  CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "CT400", "채팅을 찾을 수 없습니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND,"CT401", "해당 게시글은 존재하지 않습니다"),
  CHAT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CT401", "채팅팅 접근권한이 존재하지 않습니다."),

  CHAT_ROOM_FOUND(HttpStatus.OK, "CT210", "채팅방을 성공적으로 조회하였습니다."),
  CHAT_ROOM_UPDATE(HttpStatus.OK, "CT211", "채팅방을 성공적으로 수정하였습니다."),
  CHAT_ROOM_DELETE(HttpStatus.OK, "CT212", "채팅방을 성공적으로 삭제하였습니다."),
  CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CT400", "채팅을 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ChatCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}

