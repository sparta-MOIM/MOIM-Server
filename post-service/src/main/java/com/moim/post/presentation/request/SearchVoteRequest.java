package com.moim.post.presentation.request;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record SearchVoteRequest(
    Optional<UUID> organizationId,
    Optional<String> word,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end
){
  public SearchVoteRequest(
      UUID organizationId,
      String word,
      LocalDateTime start,
      LocalDateTime end
  ){
    this(
        Optional.ofNullable(organizationId),
        Optional.ofNullable(word),
        Optional.ofNullable(start),
        Optional.ofNullable(end)
    );
  }
}

