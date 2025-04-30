package com.sparta.moim.session.session.application.dto.result;

import java.util.UUID;

public record GetSessionMemberListResult(
    UUID id,
    String type
) {
}
