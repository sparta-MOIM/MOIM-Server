package com.moim.post.application.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateVoteCommand(
    UUID organizationId,
    String title,
    String content,
    LocalDateTime start,
    LocalDateTime end,
    Integer totalVoter
) {
}
