package com.sparta.moim.session.shared.dto;

import java.util.UUID;

public record SharedDecreaseMember(UUID sessionId, String memberId) {
}
