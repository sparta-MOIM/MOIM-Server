package com.sparta.moim.user.application.dto;

import java.util.UUID;

public record UpdateUserCommand(
    UUID trackingId,
    String name
) {
}
