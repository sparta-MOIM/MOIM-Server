package com.moim.post.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeleteRequest(
    @NotNull UUID organizationId,
    @NotNull UUID postId
){
}

