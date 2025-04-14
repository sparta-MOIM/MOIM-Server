package com.sparata.moim.gathering.gathering.application.dto.command;

import com.sparata.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchGatheringCommand(
    String name,
    boolean status,
    Boolean isDeleted,
    LocalDateTime startTime,
    LocalDateTime endTime,
    // 계정 정보
    String username,
    UUID userId,
    String role,
    Integer page, Integer size, String sort
) {
  public SearchGatheringCriteria toCriteria() {
    return SearchGatheringCriteria.builder()
        .name(name)
        .status(status)
        .isDeleted(isDeleted)
        .startTime(startTime)
        .endTime(endTime)
        .username(username)
        .userId(userId)
        .role(role)
        .page(page)
        .size(size)
        .sort(sort)
        .build();
  }
}
