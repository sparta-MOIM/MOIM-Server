package com.sparta.moim.gathering.gathering.application.dto.result;

import java.util.List;
import lombok.Builder;

@Builder
public record SearchGatheringResult(
    List<SearchGatheringListResult> gatherings,
    long total,
    int page,
    int content
) {
  public static SearchGatheringResult search(List<SearchGatheringListResult> contents, long total, int page, int content) {
    return new SearchGatheringResult(contents, total, page, content);
  }
}
