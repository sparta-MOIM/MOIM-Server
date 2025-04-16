package com.sparta.moim.gathering.gathering.presentation.dto.response;

import com.sparta.moim.gathering.gathering.application.dto.result.SearchGatheringListResult;
import java.util.UUID;

public record SearchGatheringListResponse(
    UUID gatheringId,
    String organizationId,
    String name,
    int count,
    boolean status
) {
  public SearchGatheringListResponse(SearchGatheringListResult query) {
    this(query.gatheringId(), query.organizationId(), query.name(), query.count(), query.status());
  }
}
