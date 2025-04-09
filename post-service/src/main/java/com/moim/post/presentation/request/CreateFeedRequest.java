package com.moim.post.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CreateFeedRequest(
    @NotNull UUID organizationId,
    @NotNull String title,
    @NotNull String context,
    String imageUrl,
    List<UUID> taggedIds
) {
}
