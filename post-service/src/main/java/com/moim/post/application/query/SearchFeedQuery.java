package com.moim.post.application.query;

import java.util.Optional;
import java.util.UUID;

public record SearchFeedQuery(
    Optional<UUID> organizationId,
    Optional<String> word
) {
}
