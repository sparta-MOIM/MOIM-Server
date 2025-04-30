package com.moim.post.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UpdateVoteRequest(
    @NotNull UUID organizationId,
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end,
    Optional<Integer> totalVoter
){
  public UpdateVoteRequest(
      UUID organizationId,
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end,
      Integer totalVoter
  ){
    this(
        organizationId,
        Optional.ofNullable(title),
        Optional.ofNullable(content),
        Optional.ofNullable(start),
        Optional.ofNullable(end),
        Optional.ofNullable(totalVoter)
    );
  }
}

