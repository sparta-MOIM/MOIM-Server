package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.session.domain.entity.Session;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchSessionResult(
    List<SearchSessionListResult> sessions,
    long total,
    int page,
    int content
) {
  public static SearchSessionResult search(List<Session> sessions, long total, int page, int content) {
    return SearchSessionResult.builder()
        .sessions(
            sessions.stream().map(session ->
                    SearchSessionListResult.builder()
                        .title(session.getTitle())
                        .publisher(session.getPublisher())
                        .build())
                .toList())
        .total(total)
        .page(page)
        .content(content)
        .build();
  }
}
