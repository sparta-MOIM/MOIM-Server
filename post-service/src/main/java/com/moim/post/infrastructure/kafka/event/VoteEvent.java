package com.moim.post.infrastructure.kafka.event;

import com.moim.post.infrastructure.persistence.outbox.VotePayload;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEvent implements Serializable {

  private UUID eventId;
  private UUID trackingId;
  private UUID organizationId;
  private String title;
  private String content;
  private LocalDateTime start;
  private LocalDateTime end;
  private Integer totalVoter;
  private Boolean isDeleted;

  public static VoteEvent create(
      UUID trackingId,
      UUID organizationId,
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end,
      Integer totalVoter,
      boolean isDeleted
  ) {
    return VoteEvent.builder()
        .trackingId(trackingId)
        .organizationId(organizationId)
        .title(title)
        .content(content)
        .start(start)
        .end(end)
        .totalVoter(totalVoter)
        .isDeleted(isDeleted)
        .build();
  }

  public static VoteEvent toEvent(UUID payloadId, VotePayload payload) {
    return VoteEvent.builder()
        .eventId(payloadId)
        .trackingId(payload.trackingId())
        .organizationId(payload.organizationId())
        .title(payload.title())
        .content(payload.content())
        .start(payload.start())
        .end(payload.end())
        .totalVoter(payload.totalVoter())
        .isDeleted(payload.isDeleted())
        .build();

  }
}
