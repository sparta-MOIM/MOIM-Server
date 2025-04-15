package com.sparta.moim.gathering.gathering.domain.dto.criteria;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchGatheringCriteria(String name,
                                      boolean status,
                                      Boolean isDeleted,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime,
                                      // 계정 정보
                                      String username,
                                      UUID userId,
                                      String role,
                                      Integer page, Integer size, String sort) {
}
