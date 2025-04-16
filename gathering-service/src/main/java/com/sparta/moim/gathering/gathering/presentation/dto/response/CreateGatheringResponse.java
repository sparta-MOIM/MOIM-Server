package com.sparta.moim.gathering.gathering.presentation.dto.response;

import com.sparta.moim.gathering.gathering.application.dto.result.CreateGatheringResult;
import java.util.UUID;

public record CreateGatheringResponse(
    UUID gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status
) {
  public static CreateGatheringResponse create(CreateGatheringResult query) {
    return new CreateGatheringResponse(query.gatheringId(),
                                       query.organizationId(),
                                       query.name(),
                                       query.owner(),
                                       query.count(),
                                       query.status());
  }
}
