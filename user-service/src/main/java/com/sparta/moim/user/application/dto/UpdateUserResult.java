package com.sparta.moim.user.application.dto;

import java.util.UUID;

public record UpdateUserResult(
    UUID trackingId,
    String name
) {
}
