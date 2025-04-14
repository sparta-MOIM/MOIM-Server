package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateSessionResponse(
    String organizationId,
    UUID sessionId,
    String publisher,
    String title,
    int totalCount,
    String status,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    LocalDateTime applyTime
) {
  public static CreateSessionResponse create(CreateSessionResult result) {
    return CreateSessionResponse.builder()
        .organizationId(result.organizationId())
        .sessionId(result.sessionId())
        .publisher(result.publisher())
        .title(result.title())
        .totalCount(result.totalCount())
        .status(result.status().name())
        .applyTime(result.applyTime())
        .openTime(result.openTime())
        .closeTime(result.closeTime())
        .build();
  }
}
