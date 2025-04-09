package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateSessionRequest(
    String organizationId,
    String publisher,
    String title,
    int count,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    CreateSessionApplyRequest applyInfo
) {
  public CreateSessionCommand toCommand(String userId, String role) {
    return CreateSessionCommand.builder()
        .organizationId(organizationId)
        .title(title)
        .count(count)
        .status(role.equals("USER") ? "READY" : "OPEN")
        .openTime(openTime)
        .closeTime(closeTime)
        .role(role)
        .reason(role.equals("ADMIN") && applyInfo.reason() == null ? "관리자가 생성한 세션입니다." : applyInfo.reason())
        .publisher(publisher == null ? userId : publisher)
        .build();
  }
}
