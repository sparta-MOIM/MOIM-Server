package com.moim.post.application.query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record SearchVoteQuery(
    Optional<UUID> organizationId,
    Optional<String> word,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end
) {
}
