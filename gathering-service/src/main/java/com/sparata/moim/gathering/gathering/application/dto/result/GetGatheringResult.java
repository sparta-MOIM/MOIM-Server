package com.sparata.moim.gathering.gathering.application.dto.result;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetGatheringResult(
    UUID gatheringId,
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
  public static GetGatheringResult get(Gathering gathering) {
    return new GetGatheringResult(
        gathering.getTrackingId(),
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
