package com.moim.post.application.event;

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
  private List<UUID> notifiedUserId;

  public static PostNotificationEvent create(UUID postId, List<UUID> notifiedUserId) {
    return PostNotificationEvent.builder()
        .postId(postId)
        .notifiedUserId(notifiedUserId)
        .build();
  }
}
