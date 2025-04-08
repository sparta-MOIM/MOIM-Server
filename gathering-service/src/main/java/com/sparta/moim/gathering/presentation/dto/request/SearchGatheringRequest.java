package com.sparta.moim.gathering.presentation.dto.request;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.application.dto.command.SearchGatheringCommand;
import java.time.LocalDateTime;

public record SearchGatheringRequest(
    String name, // 이름
    boolean status, // 상태
    Boolean isDeleted, // 삭제여부
    LocalDateTime startTime, // 시작 시간
    LocalDateTime endTime, // 종료 시간

    Integer page, Integer size, String sort
) {
  public SearchGatheringRequest {
    page = page == null ? 0 : page;
    size = size == null ? 10 : size;
    sort = sort == null ? "createdAt" : sort;
    startTime = startTime == null ? LocalDateTime.now() : startTime;
  }

  public SearchGatheringCommand toCommand(CustomUserDetails details) {
    return SearchGatheringCommand.builder()
        .name(name)
        .status(status)
        .isDeleted(isDeleted)
        .startTime(startTime)
        .endTime(endTime)
        .username(details.getUsername())
        .role(details.getRole())
        .userId(details.getId())
        .page(page)
        .size(size)
        .sort(sort)
        .build();
  }
}
