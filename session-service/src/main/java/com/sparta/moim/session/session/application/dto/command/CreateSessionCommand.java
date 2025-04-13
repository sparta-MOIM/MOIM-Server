package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.shared.enums.SessionStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateSessionCommand(
    String organizationId,
    String title,
    int totalCount,
    String status,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    String reason,
    String publisher,
    String role
) {
  public Session toDomain() {
    return Session.builder()
        .organizationId(organizationId)
        .title(title)
        .totalCount(totalCount)
        .status(SessionStatus.valueOf(status))
        .openTime(openTime)
        .currentCount(1)
        .closeTime(closeTime)
        .applyTime(LocalDateTime.now())
        .confirmTime(role.equals("USER") ? null : LocalDateTime.now())
        .reason(reason)
        .publisher(publisher)
        .build();
  }
}
