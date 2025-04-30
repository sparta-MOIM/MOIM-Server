package com.moim.post.domain.feed.view;

import com.moim.post.infrastructure.kafka.event.FeedEvent;
import jakarta.persistence.Id;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "feed")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedView {

  @Id
  private String id;

  private UUID trackingId;

  private UUID organizationId;

  private String title;

  private String content;

  private String imageUrl;

  private Boolean isDeleted;

  private List<UUID> taggedUserIds;

  public static FeedView create(
      UUID trackingId,
      UUID organizationId,
      String title,
      String content,
      String imageUrl,
      Boolean isDeleted,
      List<UUID> taggedUserIds
  ) {
    return FeedView.builder()
        .trackingId(trackingId)
        .organizationId(organizationId)
        .title(title)
        .content(content)
        .imageUrl(imageUrl)
        .isDeleted(isDeleted)
        .taggedUserIds(taggedUserIds)
        .build();
  }

  public static FeedView toView(FeedEvent event) {
    return FeedView.create(
        event.getTrackingId(),
        event.getOrganizationId(),
        event.getTitle(),
        event.getContent(),
        event.getImageUrl(),
        event.getIsDeleted(),
        event.getTaggedUserIds());
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateContent(String content) {
    this.content = content;
  }

  public void updateImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void delete() {
    this.isDeleted = true;
  }

}
