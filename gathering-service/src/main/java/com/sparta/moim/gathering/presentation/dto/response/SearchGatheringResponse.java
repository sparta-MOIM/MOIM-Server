package com.sparta.moim.gathering.presentation.dto.response;

import com.sparta.moim.gathering.application.dto.query.SearchGatheringQuery;
import java.util.List;

public record SearchGatheringResponse(
    List<SearchGatheringListResponse> gatherings,
    long total,
    int page,
    int content
) {
  public static SearchGatheringResponse search(SearchGatheringQuery query) {
    return new SearchGatheringResponse(query.gatherings().stream().map(SearchGatheringListResponse::new).toList(),
                                       query.total(),
                                       query.page(),
                                       query.content());
  }
}
