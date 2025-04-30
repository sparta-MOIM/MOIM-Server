package com.moim.post.infrastructure.kafka.event;

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
public class PostNotificationEvent implements Serializable {
  private UUID postId;
  private List<UUID> notifiedUserIds;

  public static PostNotificationEvent create(UUID postId, List<UUID> notifiedUserIds) {
    return PostNotificationEvent.builder()
        .postId(postId)
        .notifiedUserIds(notifiedUserIds)
        .build();
  }
}
