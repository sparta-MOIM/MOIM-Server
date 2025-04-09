package com.sparta.moim.common.response;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code implements BaseError {
  /**
   * 성공
   * 200번대
   */
  SUCCESS(HttpStatus.OK, 200, "성공적으로 처리되었습니다."),
  CREATED(HttpStatus.CREATED, 201, "성공적으로 생성되었습니다."),
  /**
   * 500번대
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),
  INTERNAL_SERVER_MINIO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Minio 서버 오류가 발생했습니다."),

  /**
   * ORGANIZATION
   */
  ORGANIZATION_CANNOT_FIND_ORGANIZATION(HttpStatus.BAD_REQUEST, 40000 , "모임을 찾을 수 없습니다."),
  ORGANIZATION_CANNOT_FIND_ORGANIZATION_MEMBER(HttpStatus.FORBIDDEN, 40001 , "모임 구성원이 아닙니다."),
  ORGANIZATION_NOT_MASTER(HttpStatus.FORBIDDEN, 40002 , "모임의 MASTER가 아닙니다."),
  ORGANIZATION_NOT_MANAGER(HttpStatus.FORBIDDEN, 40003 , "모임의 MANAGER가 아닙니다."),
  ORGANIZATION_ALREADY_MEMBER(HttpStatus.BAD_REQUEST,40004 ,"이미 모임의 멤버입니다." ),
  ORGANIZATION_CANNOT_FIND_ORGANIZATION_APPLICATION(HttpStatus.BAD_REQUEST,40005 , "모임 신청을 찾을 수 없습니다." ),
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
