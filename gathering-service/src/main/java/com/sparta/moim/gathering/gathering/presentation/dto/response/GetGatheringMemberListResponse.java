package com.sparta.moim.gathering.gathering.presentation.dto.response;

import java.util.UUID;

public record GetGatheringMemberListResponse(
    UUID memberId,
    String type
) {
}
