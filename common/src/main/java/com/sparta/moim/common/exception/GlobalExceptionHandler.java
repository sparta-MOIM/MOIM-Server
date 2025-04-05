package com.sparta.moim.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.sparta.moim.common.response.ApiResponseData;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        .body(ApiResponseData.failure(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
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
    return ResponseEntity.status(BAD_REQUEST).body(ApiResponseData.failure(0, errorMessages));
  }
}