package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetSessionResult(
    String organizationId,
    UUID sessionId,
    String title,
    String publisher,
    int count,
    List<GetSessionMemberListResult> members,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    SessionStatus status,
    LocalDateTime applyTime,
    LocalDateTime confirmTime,
    String reason
) {
  public static GetSessionResult get(Session session, List<GetSessionMemberListResult> members) {
    return GetSessionResult.builder()
        .organizationId(session.getOrganizationId())
        .sessionId(session.getTrackingId())
        .publisher(session.getPublisher())
        .title(session.getTitle())
        .count(session.getCount())
        .members(members)
        .status(session.getStatus())
        .openTime(session.getOpenTime())
        .closeTime(session.getCloseTime())
        .applyTime(session.getApplyTime())
        .confirmTime(session.getConfirmTime())
        .reason(session.getReason())
        .build();
  }
}
