package com.sparta.moim.session.member.application.dto.command;

import java.util.UUID;

public record LeaveMemberCommand(UUID sessionId, UUID userId) {
}
