package com.sparta.moim.gathering.application.dto.query;

import java.util.List;
import java.util.stream.Stream;
import lombok.Builder;

@Builder
public record SearchGatheringQuery(
    List<SearchGatheringListQuery> gatherings,
    long total,
    int page,
    int content
) {
  public static SearchGatheringQuery search(List<SearchGatheringListQuery> contents, long total, int page, int content) {
    return new SearchGatheringQuery(contents, total, page, content);
  }
}
