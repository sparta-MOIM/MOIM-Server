package com.sparta.moim.session.shared.dto;

import java.util.UUID;
import lombok.Builder;


@Builder
public record SharedSessionMember(String memberName, UUID sessionId, String type) {}
