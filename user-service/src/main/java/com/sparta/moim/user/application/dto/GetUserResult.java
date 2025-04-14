package com.sparta.moim.user.application.dto;

import java.util.UUID;

public record GetUserResult(
    UUID trackingId,
    String username,
    String name
) {
}
