package com.sparta.moim.gathering.gathering.application.dto.event;

import java.util.UUID;
import lombok.Builder;

@Builder
public record GatheringAddAdminEvent(UUID gatheringId, String memberName, String type) {
}
