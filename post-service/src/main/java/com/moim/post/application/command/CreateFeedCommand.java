package com.moim.post.application.command;

import java.util.List;
import java.util.UUID;

public record CreateFeedCommand(
    UUID organizationId,
    String title,
    String context,
    String imageUrl,
    List<UUID> taggedIds
) {
}
