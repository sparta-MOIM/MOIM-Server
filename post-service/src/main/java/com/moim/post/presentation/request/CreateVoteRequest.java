package com.moim.post.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateVoteRequest(
    @NotNull UUID organizationId,
    @NotNull String title,
    @NotNull String content,
    @NotNull LocalDateTime start,
    @NotNull LocalDateTime end,
    @NotNull Integer totalVoter
) {
}
