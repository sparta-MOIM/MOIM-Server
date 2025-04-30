package com.moim.post.infrastructure.persistence.outbox;

import com.moim.post.domain.vote.Vote;
import java.time.LocalDateTime;
import java.util.UUID;

public record VotePayload(
    UUID trackingId,
    UUID organizationId,
    String title,
    String content,
    LocalDateTime start,
    LocalDateTime end,
    Integer totalVoter,
    Boolean isDeleted
) {
  public VotePayload(Vote vote) {
    this(
        vote.getTrackingId(),
        vote.getOrganizationId(),
        vote.getTitle(),
        vote.getContent(),
        vote.getPeriod().getStart(),
        vote.getPeriod().getEnd(),
        vote.getTotalVoter(),
        vote.getIsDeleted()
    );
  }
}
