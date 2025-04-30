package com.sparta.moim.session.member.application.dto.command;

import java.util.UUID;

public record JoinMemberCommand(UUID sessionId, UUID userId) {
}
