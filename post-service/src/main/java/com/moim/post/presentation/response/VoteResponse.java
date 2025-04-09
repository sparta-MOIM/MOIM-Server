package com.moim.post.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoteResponse(
    UUID id,
    UUID organizationId,
    String title,
    String content,
    LocalDateTime start,
    LocalDateTime end,
    Integer totalVoter
) {
}
