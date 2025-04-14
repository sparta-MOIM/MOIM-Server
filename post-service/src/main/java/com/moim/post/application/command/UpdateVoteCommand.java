package com.moim.post.application.command;

import java.time.LocalDateTime;
import java.util.Optional;

public record UpdateVoteCommand(
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end,
    Optional<Integer> totalVoter
) {
}