package com.moim.post.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UpdateFeedRequest(
    @NotNull UUID organizationId,
    Optional<String> title,
    Optional<String> content,
    Optional<String> imageUrl,
    Optional<List<UUID>> taggedUserIds
){
  public UpdateFeedRequest(
      UUID organizationId,
      String title,
      String content,
      String imageUrl,
      List<UUID> taggedUserIds
  ){
    this(
        organizationId,
        Optional.ofNullable(title),
        Optional.ofNullable(content),
        Optional.ofNullable(imageUrl),
        Optional.ofNullable(taggedUserIds)
    );
  }
}

