package com.sparta.moim.gathering.shared.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SharedGatheringMember(UUID gatheringId, String memberName, String type) {
}
