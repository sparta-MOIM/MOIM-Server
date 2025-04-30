package com.sparta.moim.gathering.gathering.presentation.dto.response;

import java.util.UUID;

public record GetMemberListResponse(
    UUID name,
    String type
) {
}
