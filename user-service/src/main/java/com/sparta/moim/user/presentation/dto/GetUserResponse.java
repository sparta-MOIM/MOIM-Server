package com.sparta.moim.user.presentation.dto;

import java.util.UUID;

public record GetUserResponse(
    UUID trackingId,
    String username,
    String name
) {
}
