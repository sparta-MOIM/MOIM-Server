package com.sparta.moim.common.response;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
  /**
   * 성공
   * 200번대
   */
  SUCCESS(HttpStatus.OK, 200, "성공적으로 처리되었습니다."),
  CREATED(HttpStatus.CREATED, 201, "성공적으로 생성되었습니다."),
  ALREADY_EXISTS(HttpStatus.OK, 202, "이미 존재하는 리소스입니다."),

  /**
   * 500번대
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),
  INTERNAL_SERVER_MINIO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Minio 서버 오류가 발생했습니다."),
  ;

  private final HttpStatus status;
  private final Integer code;
  private final String message;

  public String getMessage(Throwable e) {
    return this.getMessage(this.getMessage() + " - " + e.getMessage());
    // 결과 예시 - "Validation error - Reason why it isn't valid"
  }

  public String getMessage(String message) {
    return Optional.ofNullable(message)
        .filter(Predicate.not(String::isBlank))
        .orElse(this.getMessage());
  }

  public String getDetailMessage(String message) {
    return this.getMessage() + " : " + message;
  }
}
