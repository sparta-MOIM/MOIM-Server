package com.sparata.moim.gathering.gathering.presentation.dto.response;

import com.sparata.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetGatheringResponse(
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
  public static GetGatheringResponse get(GetGatheringResult query) {
    return new GetGatheringResponse(query.gatheringId(),
        query.organizationId(),
        query.name(),
        query.owner(),
        query.count(),
        query.status(),
        query.createAt(),
        query.createBy(),
        query.updateAt(),
        query.updateBy()
    );
  }
}
