package com.sparta.moim.session.session.application.dto.command;

import java.util.UUID;

public record DeleteSessionCommand(UUID sessionId, String username) {
}
