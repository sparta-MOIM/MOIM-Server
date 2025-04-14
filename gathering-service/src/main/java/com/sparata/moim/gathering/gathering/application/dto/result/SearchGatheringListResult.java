package com.sparata.moim.gathering.gathering.application.dto.result;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchGatheringListResult(
    UUID gatheringId,
    String organizationId,
    String name,
    int count,
    boolean status
) {
  public SearchGatheringListResult(Gathering gathering) {
    this(gathering.getTrackingId(), gathering.getOrganizationId(), gathering.getName(), gathering.getCount(),
        gathering.getStatus());
  }
}
