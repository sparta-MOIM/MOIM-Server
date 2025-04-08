package com.sparta.moim.gathering.presentation.dto.response;

import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import java.util.UUID;

public record CreateGatheringResponse(
    UUID gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status
) {
  public static CreateGatheringResponse create(CreateGatheringQuery query) {
    return new CreateGatheringResponse(query.gatheringId(),
                                       query.organizationId(),
                                       query.name(),
                                       query.owner(),
                                       query.count(),
                                       query.status());
  }
}
