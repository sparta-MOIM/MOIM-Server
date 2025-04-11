package com.sparta.moim.organization.presentation.code;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 공통적으로 사용할 기본 코드를 구현체로 구현
@Getter
public enum OrganizationCode implements Code {

  SUCCESS(HttpStatus.OK, "ORG200", "성공적으로 처리되었습니다."),
  CREATED(HttpStatus.CREATED, "ORG201", "성공적으로 생성되었습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ORG500", "예기치 못한 서버 오류가 발생했습니다."),

  CANNOT_FIND_ORGANIZATION(HttpStatus.BAD_REQUEST, "ORG0001" , "모임을 찾을 수 없습니다."),
  CANNOT_FIND_ORGANIZATION_MEMBER(HttpStatus.FORBIDDEN, "ORG002", "모임 구성원이 아닙니다."),
  NOT_ORGANIZATION_MASTER(HttpStatus.FORBIDDEN, "ORG003", "모임의 MASTER가 아닙니다."),
  NOT_ORGANIZATION_MANAGER(HttpStatus.FORBIDDEN,"ORG004" , "모임의 MANAGER가 아닙니다."),
  ALREADY_ORGANIZATION_MEMBER(HttpStatus.BAD_REQUEST,"ORG005" ,"이미 모임의 멤버입니다." ),
  CANNOT_FIND_ORGANIZATION_APPLICATION(HttpStatus.BAD_REQUEST,"ORG006" , "모임 신청을 찾을 수 없습니다." ),
  ALREADY_USED_ORGANIZATION_NICKNAME(HttpStatus.BAD_REQUEST,"ORG007" ,"이미 사용중인 닉네임입니다." ),
  MEMBER_ACCESS_DENIED( HttpStatus.FORBIDDEN, "ORG008", "모임에 해당 권한이 없습니다."),;

  private final HttpStatus status;
  private final String code;
  private final String message;

  OrganizationCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
