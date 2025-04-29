package com.sparta.moim.session.shared.dto;

import java.util.UUID;

public record SharedIncreaseMember(UUID sessionId, UUID memberId) {
}
