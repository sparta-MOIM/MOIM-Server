package com.sparta.moim.gathering.application.dto.query;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.util.UUID;

public record CreateGatheringQuery(
    Long gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status
) {
  public static CreateGatheringQuery create(Gathering gathering) {
    return new CreateGatheringQuery(gathering.getId(),
                                    gathering.getOrganizationId(),
                                    gathering.getName(),
                                    gathering.getOwner(),
                                    gathering.getCount(),
                                    gathering.getStatus());
  }
}
