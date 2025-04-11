package com.sparta.moim.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.sparta.moim.common.response.ApiResponseData;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  //스프링에서 감지하는 에러들
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("런타임 오류 발생: " + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("예상치 못한 오류 발생: " + e.getMessage());
  }

  // 커스텀 에러처리 가능 (아래 예외 핸들러 추가 하면 됨)
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ApiResponseData<String>> handleBaseException(BaseException e) {
    return ResponseEntity.status(BAD_REQUEST)
        .body(ApiResponseData.failure(e.getCode().getCode(), e.getCode().getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseData<Object>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    StringBuilder sb = new StringBuilder();
    e.getBindingResult().getFieldErrors()
        .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .forEach(message -> sb.append(message).append("\n"));

    if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
      sb.deleteCharAt(sb.length() - 1);  // 마지막 문자가 개행 문자라면 삭제
    }

    String errorMessages = sb.toString();
    return ResponseEntity.status(BAD_REQUEST).body(ApiResponseData.failure("0", errorMessages));
  }

  /**
   * 접근 권한이 없을 경우 발생하는 {@code AccessDeniedException}을 처리합니다.
   * 
   * 이 메서드는 HTTP 403(FORBIDDEN) 상태와 함께, 상태 이름(문자열 "FORBIDDEN")과 "접근 권한이 없습니다." 메시지를 포함한 실패 응답 데이터를 반환합니다.
   *
   * @param e 접근 권한이 없음을 나타내는 예외
   * @return 403 상태 및 관련 실패 응답 데이터를 포함하는 ResponseEntity
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponseData<Object>> handleAccessDeniedException(AccessDeniedException e) {
    return ResponseEntity.status(FORBIDDEN).body(
        ApiResponseData.failure(FORBIDDEN.name(), "접근 권한이 없습니다."));
  }
}
