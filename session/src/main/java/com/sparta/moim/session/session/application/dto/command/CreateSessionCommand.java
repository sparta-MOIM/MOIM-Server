package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateSessionCommand(
    String organizationId,
    String name,
    int count,
    String status,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    String reason,
    String publisher
) {
  public Session toDomain() {
    return Session.builder()
        .organizationId(organizationId)
        .name(name)
        .count(count)
        .status(SessionStatus.valueOf(status))
        .openTime(openTime)
        .closeTime(closeTime)
        .reason(reason == null ? "관리자가 생성한 세션입니다." : reason)
        .publisher(publisher)
        .build();
  }
}
