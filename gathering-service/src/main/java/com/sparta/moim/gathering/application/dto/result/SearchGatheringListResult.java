package com.sparta.moim.gathering.application.dto.result;

import com.sparta.moim.gathering.domain.entity.Gathering;
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
