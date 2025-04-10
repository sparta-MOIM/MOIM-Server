package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.dto.crtria.SearchSessionCriteria;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchSessionCommand(
    String title,
    String publisher,
    String status,
    String reason,
    LocalDateTime openTime,
    LocalDateTime closeTime,
    Boolean isDeleted,
    LocalDateTime applyTime,
    LocalDateTime confirmTime,
    String role,
    Integer page, Integer size, String sort
) {
  public SearchSessionCriteria toCriteria() {
    return SearchSessionCriteria.builder()
        .title(title)
        .publisher(publisher)
        .status(status == null ? null : SessionStatus.valueOf(status))
        .reason(reason)
        .role(role)
        .isDeleted(isDeleted)
        .openTime(openTime)
        .closeTime(closeTime)
        .applyTime(applyTime)
        .confirmTime(confirmTime)
        .page(page)
        .size(size)
        .sort(sort)
        .build();
  }
}
