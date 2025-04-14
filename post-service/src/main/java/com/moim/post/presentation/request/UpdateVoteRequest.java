package com.moim.post.presentation.request;

import java.time.LocalDateTime;
import java.util.Optional;

public record UpdateVoteRequest(
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end,
    Optional<Integer> totalVoter
){
  public UpdateVoteRequest(
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end,
      Integer totalVoter
  ){
    this(
        Optional.ofNullable(title),
        Optional.ofNullable(content),
        Optional.ofNullable(start),
        Optional.ofNullable(end),
        Optional.ofNullable(totalVoter)
    );
  }
}

