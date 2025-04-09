package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateSessionRequest(
    String organizationId,
    String publisher,
    String name,
    int count,
    String status,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    CreateSessionApplyRequest applyInfo
) {
  public CreateSessionCommand toCommand(String userId, String role) {
    return CreateSessionCommand.builder()
        .organizationId(organizationId)
        .name(name)
        .count(count)
        .status(role.equals("USER") ? "READY" : status)
        .openTime(openTime)
        .closeTime(closeTime)
        .reason(applyInfo.reason())
        .publisher(publisher == null ? userId : publisher)
        .build();
  }
}
