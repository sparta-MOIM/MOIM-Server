package com.moim.post.presentation.request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UpdateFeedRequest(
    Optional<String> title,
    Optional<String> content,
    Optional<String> imageUrl,
    Optional<List<UUID>> taggedUserIds
){
  public UpdateFeedRequest(
      String title,
      String content,
      String imageUrl,
      List<UUID> taggedUserIds
  ){
    this(
        Optional.ofNullable(title),
        Optional.ofNullable(content),
        Optional.ofNullable(imageUrl),
        Optional.ofNullable(taggedUserIds)
    );
  }
}

