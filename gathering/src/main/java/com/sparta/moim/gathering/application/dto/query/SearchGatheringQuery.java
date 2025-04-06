package com.sparta.moim.gathering.application.dto.query;

import java.util.List;
import java.util.stream.Stream;

public record SearchGatheringQuery(
    List<SearchGatheringListQuery> gatherings,
    int total,
    int page,
    int content
) {
  public static SearchGatheringQuery search(List<SearchGatheringListQuery> contents, int total, int page, int content) {
    return new SearchGatheringQuery(contents, total, page, content);
  }
}
