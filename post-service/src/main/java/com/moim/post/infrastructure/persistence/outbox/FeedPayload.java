package com.moim.post.infrastructure.persistence.outbox;

import com.moim.post.domain.feed.Feed;
import java.util.List;
import java.util.UUID;

public record FeedPayload(
    UUID trackingId,
    UUID organizationId,
    String title,
    String content,
    String imageUrl,
    Boolean isDeleted,
    List<UUID> taggedUserIds
) {
  public FeedPayload(Feed feed) {
    this(
        feed.getTrackingId(),
        feed.getOrganizationId(),
        feed.getTitle(),
        feed.getContent(),
        feed.getImageUrl(),
        feed.getIsDeleted(),
        feed.getTaggedUserIds()
    );
  }
}
