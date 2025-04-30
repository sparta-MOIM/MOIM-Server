package com.moim.post.infrastructure.kafka.event;

import com.moim.post.infrastructure.persistence.outbox.FeedPayload;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedEvent implements Serializable {

  private UUID eventId;
  private UUID trackingId;
  private UUID organizationId;
  private String title;
  private String content;
  private String imageUrl;
  private Boolean isDeleted;
  private List<UUID> taggedUserIds;

  public static FeedEvent create(
      UUID trackingId,
      UUID organizationId,
      String title,
      String content,
      String imageUrl,
      boolean isDeleted,
      List<UUID> taggedUserIds
  ) {
    return FeedEvent.builder()
        .trackingId(trackingId)
        .organizationId(organizationId)
        .title(title)
        .content(content)
        .imageUrl(imageUrl)
        .title(title)
        .isDeleted(isDeleted)
        .taggedUserIds(taggedUserIds)
        .build();
  }

  public static FeedEvent toEvent(UUID payloadId, FeedPayload payload) {
    return FeedEvent.builder()
        .eventId(payloadId)
        .trackingId(payload.trackingId())
        .organizationId(payload.organizationId())
        .title(payload.title())
        .content(payload.content())
        .imageUrl(payload.imageUrl())
        .title(payload.title())
        .isDeleted(payload.isDeleted())
        .taggedUserIds(payload.taggedUserIds())
        .build();

  }
}
