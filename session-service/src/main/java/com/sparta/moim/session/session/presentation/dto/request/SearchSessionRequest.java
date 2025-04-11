package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.SearchSessionCommand;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchSessionRequest(
    String title,
    String publisher,
    String status,
    String reason,
    LocalDateTime openTime,
    LocalDateTime closeTime,

    LocalDateTime applyTime,
    Boolean isDeleted,
    LocalDateTime confirmTime,
    Integer page, Integer size, String sort

) {

  public SearchSessionRequest {
    page = page == null ? 0 : page;
    size = size == null ? 10 : size;
    sort = sort == null ? "createdAt" : sort;

  }

  public SearchSessionCommand toCommand(String role) {
    return SearchSessionCommand.builder()
        .title(title)
        .publisher(publisher)
        .status(status)
        .reason(reason)
        .openTime(openTime)
        .closeTime(closeTime)
        .applyTime(applyTime)
        .confirmTime(confirmTime)
        .role(role)
        .page(page)
        .size(size)
        .sort(sort)
        .build();
  }
}
