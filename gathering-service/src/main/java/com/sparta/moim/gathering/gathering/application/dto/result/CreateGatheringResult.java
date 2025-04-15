package com.sparta.moim.gathering.gathering.application.dto.result;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.util.UUID;

public record CreateGatheringResult(
    UUID gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status
) {
  public static CreateGatheringResult create(Gathering gathering) {
    return new CreateGatheringResult(gathering.getTrackingId(),
                                    gathering.getOrganizationId(),
                                    gathering.getName(),
                                    gathering.getOwner(),
                                    gathering.getCount(),
                                    gathering.getStatus());
  }
}
