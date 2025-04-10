package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchSessionResponse(
    List<SearchSessionListResponse> sessions,
    long total,
    int page,
    int content
) {
  public static SearchSessionResponse search(SearchSessionResult result) {
    return SearchSessionResponse.builder()
        .sessions(result.sessions().stream().map(session ->
            SearchSessionListResponse.builder()
                .title(session.title())
                .publisher(session.publisher())
                .build()).toList())
        .total(result.total())
        .page(result.page())
        .content(result.content())
        .build();
  }
}
