package com.sparta.moim.notificationservice.presentation.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 공통적으로 사용할 기본 코드를 구현체로 구현
@Getter
public enum NotificationCode implements Code {

  SUCCESS(HttpStatus.OK, "NOT200", "성공적으로 처리되었습니다."),
  CREATED(HttpStatus.CREATED, "NOT201", "성공적으로 생성되었습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NOT500", "예기치 못한 서버 오류가 발생했습니다."),

  CANNOT_FIND_NOTIFICATION_TEMPLATE(HttpStatus.BAD_REQUEST, "NOT0001" , "알림 템플릿을 찾을 수 없습니다."),
  CANNOT_FIND_NOTIFICATION(HttpStatus.BAD_REQUEST,"NOT002" , "알림을 찾을 수 없습니다."),
  ;
  private final HttpStatus status;
  private final String code;
  private final String message;

  NotificationCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
