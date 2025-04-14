package com.moim.post.presentation.request;

import java.util.Optional;
import java.util.UUID;

public record SearchFeedRequest(
    Optional<UUID> organizationId,
    Optional<String> word
){
  public SearchFeedRequest(
      UUID organizationId,
      String word
  ){
    this(
        Optional.ofNullable(organizationId),
        Optional.ofNullable(word)
    );
  }
}

