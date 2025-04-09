package com.moim.post.presentation.response;

import java.util.List;
import java.util.UUID;

public record FeedResponse(
    UUID id,
    UUID organizationId,
    String title,
    String context,
    String imageUrl,
    List<UUID> taggedUserIds
) {
}
