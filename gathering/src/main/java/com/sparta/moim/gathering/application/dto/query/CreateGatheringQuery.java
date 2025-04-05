package com.sparta.moim.gathering.application.dto.query;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.util.UUID;

public record CreateGatheringQuery(
    UUID gatheringId,
    String organizationId,
    String name,
    int count,
    boolean Status
) {
  public static CreateGatheringQuery create(Gathering gathering) {
    return new CreateGatheringQuery(gathering.getId(),
                                    gathering.getOrganizationId(),
                                    gathering.getName(),
                                    gathering.getCount(),
                                    gathering.isStatus());
  }
}
