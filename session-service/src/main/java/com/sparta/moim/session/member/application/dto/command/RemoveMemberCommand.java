package com.sparta.moim.session.member.application.dto.command;

import java.util.List;
import java.util.UUID;

public record RemoveMemberCommand(UUID sessionId, List<UUID> members) {
}
