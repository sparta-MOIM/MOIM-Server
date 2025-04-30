package com.moim.post.domain.vote.view;

import com.moim.post.infrastructure.kafka.event.VoteEvent;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "vote")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteView {

  @Id
  private String id;

  private UUID trackingId;

  private UUID organizationId;

  private String title;

  private String content;

  private LocalDateTime start;

  private LocalDateTime end;

  private Integer totalVoter;

  private Boolean isDeleted;

  public static VoteView create(
      UUID trackingId,
      UUID organizationId,
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end,
      Integer totalVoter,
      Boolean isDeleted
  ) {
    return VoteView.builder()
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

  public static VoteView toView(VoteEvent event) {
    return VoteView.create(
        event.getTrackingId(),
        event.getOrganizationId(),
        event.getTitle(),
        event.getContent(),
        event.getStart(),
        event.getEnd(),
        event.getTotalVoter(),
        event.getIsDeleted());
  }

}
