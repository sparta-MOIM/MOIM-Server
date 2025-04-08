package com.sparta.moim.gathering.application.dto.query;

import com.sparta.moim.gathering.domain.entity.Gathering;
import lombok.Builder;

@Builder
public record SearchGatheringListQuery(
    Long gatheringId,
    String organizationId,
    String name,
    int count,
    boolean status
) {
  public SearchGatheringListQuery(Gathering gathering) {
    this(gathering.getId(), gathering.getOrganizationId(), gathering.getName(), gathering.getCount(),
        gathering.getStatus());
  }
}
