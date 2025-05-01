package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.shared.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.UUID;
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
    UUID publisher,
    UUID userId,
    String role
) {
  public Session toDomain(String reason, SessionStatus status) {
    return Session.builder()
        .organizationId(organizationId)
        .title(title)
        .totalCount(totalCount)
        .status(status != null ? status : SessionStatus.valueOf(this.status))
        .openTime(openTime)
        .currentCount(1)
        .closeTime(closeTime)
        .applyTime(LocalDateTime.now())
        .confirmTime(role.equals("USER") ? null : LocalDateTime.now())
        .reason(reason != null ? reason : this.reason)
        .publisher(publisher)
        .build();
  }
}
