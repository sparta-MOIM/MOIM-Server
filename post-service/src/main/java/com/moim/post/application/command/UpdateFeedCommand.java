package com.moim.post.application.command;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UpdateFeedCommand(
    UUID organizationId,
    Optional<String> title,
    Optional<String> content,
    Optional<String> imageUrl,
    Optional<List<UUID>> taggedUserIds
) {
}
