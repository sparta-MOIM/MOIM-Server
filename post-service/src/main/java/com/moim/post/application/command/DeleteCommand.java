package com.moim.post.application.command;

import java.util.UUID;

public record DeleteCommand(
    UUID organizationId,
    UUID postId
) {
}
