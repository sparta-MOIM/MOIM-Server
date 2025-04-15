package com.sparta.moim.user.presentation.dto;

import java.util.UUID;

public record UpdateUserResponse(
    UUID trackingId,
    String name
) {
}
