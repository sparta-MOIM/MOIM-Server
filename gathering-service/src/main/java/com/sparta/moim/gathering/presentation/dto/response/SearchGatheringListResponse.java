package com.sparta.moim.gathering.presentation.dto.response;

import com.sparta.moim.gathering.application.dto.query.SearchGatheringListQuery;
import java.util.UUID;

public record SearchGatheringListResponse(
    Long gatheringId,
    String organizationId,
    String name,
    int count,
    boolean status
) {
  public SearchGatheringListResponse(SearchGatheringListQuery query) {
    this(query.gatheringId(), query.organizationId(), query.name(), query.count(), query.status());
  }
}
