package com.sparta.moim.gathering.gathering.application.dto.event;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SharedGatheringMember(UUID gatheringId, String memberName, String type) {
}
