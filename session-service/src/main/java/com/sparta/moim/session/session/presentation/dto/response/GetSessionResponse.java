package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetSessionResponse(
    String organizationId,
    UUID sessionId,
    String title,
    String publisher,
    int count,
    List<GetSessionMemberListResponse> member,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    String status,
    GetSessionApplyResponse applyInfo
) {
  public static GetSessionResponse get(GetSessionResult result) {
    return GetSessionResponse.builder()
        .sessionId(result.sessionId())
        .organizationId(result.organizationId())
        .title(result.title())
        .publisher(result.publisher())
        .count(result.count())
        .member(result.members().stream().map(GetSessionMemberListResponse::new).toList())
        .openTime(result.openTime())
        .closeTime(result.closeTime())
        .status(result.status().name())
        .applyInfo(new GetSessionApplyResponse(
            result.applyTime(),
            result.confirmTime(),
            result.reason()
        ))
        .build();
  }
}
