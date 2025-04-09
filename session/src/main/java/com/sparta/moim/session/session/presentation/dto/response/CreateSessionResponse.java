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
    String name,
    int count,
    String status,
    LocalDateTime openTime,
    LocalDateTime closeTime
) {
  public static CreateSessionResponse create(CreateSessionResult result) {
    return CreateSessionResponse.builder()
        .organizationId(result.organizationId())
        .sessionId(result.sessionId())
        .publisher(result.publisher())
        .name(result.name())
        .count(result.count())
        .status(result.status().name())
        .openTime(result.openTime())
        .closeTime(result.closeTime())
        .build();
  }
}
