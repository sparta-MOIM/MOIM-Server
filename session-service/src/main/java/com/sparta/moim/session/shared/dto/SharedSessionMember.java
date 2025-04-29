package com.sparta.moim.session.shared.dto;

import java.util.UUID;
import lombok.Builder;


@Builder
public record SharedSessionMember(UUID memberId, UUID sessionId, String type) {}
