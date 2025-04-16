package com.moim.post.application.command;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UpdateVoteCommand(
    UUID organizationId,
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end,
    Optional<Integer> totalVoter
) {
}