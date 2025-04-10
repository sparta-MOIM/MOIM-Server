package com.sparta.moim.common.response;

import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.http.HttpStatus;

public interface Code {
  HttpStatus getStatus();
  String getCode();
  String getMessage();

  default String getMessage(Throwable e) {
    return this.getMessage(this.getMessage() + " - " + e.getMessage());
    // 결과 예시 - "Validation error - Reason why it isn't valid"
  }

  default String getMessage(String message) {
    return Optional.ofNullable(message)
        .filter(Predicate.not(String::isBlank))
        .orElse(this.getMessage());
  }

  default String getDetailMessage(String message) {
    return this.getMessage() + " : " + message;
  }
}
