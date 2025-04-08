package com.sparta.moim.gathering.application.dto.query;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetGatheringQuery(
    Long gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status,
    LocalDateTime createAt,
    String createBy,
    LocalDateTime updateAt,
    String updateBy
) {
  public static GetGatheringQuery get(Gathering gathering) {
    return new GetGatheringQuery(
        gathering.getId(),
        gathering.getOrganizationId(),
        gathering.getName(),
        gathering.getOwner(),
        gathering.getCount(),
        gathering.getStatus(),
        null,
        null,
        null,
        null
    );
  }
}
