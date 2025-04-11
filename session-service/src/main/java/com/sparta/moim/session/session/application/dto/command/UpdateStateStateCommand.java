package com.sparta.moim.session.session.application.dto.command;

import java.util.UUID;

public record UpdateStateStateCommand(
    UUID sessionId,
    String status
) {
}
