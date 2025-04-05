package com.sparta.moim.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPassportConstants {
  X_USER_NAME("X-User-Name"),
  X_USER_ROLE("X-User-Role"),
  X_USER_ID("X-User-ID"),
  ;

  private final String value;

}
