package com.sparta.moim.gathering.presentation.dto.response;

import com.sparta.moim.gathering.application.dto.query.GetGatheringQuery;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetGatheringResponse(
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
  public static GetGatheringResponse get(GetGatheringQuery query) {
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
