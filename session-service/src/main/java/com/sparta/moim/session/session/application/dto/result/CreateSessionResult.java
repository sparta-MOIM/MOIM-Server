package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.shared.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateSessionResult(
    String organizationId,
    UUID sessionId,
    String publisher,
    String title,
    int totalCount,
    SessionStatus status,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    LocalDateTime applyTime
) {
  public static CreateSessionResult create(Session session) {
    return CreateSessionResult.builder()
        .organizationId(session.getOrganizationId())
        .sessionId(session.getTrackingId())
        .publisher(session.getPublisher())
        .title(session.getTitle())
        .totalCount(session.getTotalCount())
        .status(session.getStatus())
        .openTime(session.getOpenTime())
        .closeTime(session.getCloseTime())
        .applyTime(session.getApplyTime())
        .build();
  }
}
