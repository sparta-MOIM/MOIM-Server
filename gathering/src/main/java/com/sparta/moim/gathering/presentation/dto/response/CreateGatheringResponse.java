package com.sparta.moim.gathering.presentation.dto.response;

import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import java.util.UUID;

public record CreateGatheringResponse(
    UUID gatheringId,
    String organizationId,
    String name,
    int count,
    boolean Status
) {
  public static CreateGatheringResponse create(CreateGatheringQuery query) {
    return new CreateGatheringResponse(query.gatheringId(),
                                       query.organizationId(),
                                       query.name(),
                                       query.count(),
                                       query.Status());
  }
}
