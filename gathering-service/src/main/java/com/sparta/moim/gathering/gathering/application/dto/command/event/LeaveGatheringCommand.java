package com.sparta.moim.gathering.gathering.application.dto.command.event;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.GatheringEventCriteria;
import com.sparta.moim.gathering.shared.enums.EventType;
import java.util.Map;
import java.util.UUID;

public record LeaveGatheringCommand(UUID gatheringId, String username) {
  public LeaveGatheringCommand(UUID gatheringId, CustomUserDetails userInfo) {
    this(gatheringId, userInfo.getUsername());
  }

  public GatheringEventCriteria toCriteria(String streamLeaveKey, EventType eventType, String payload) {
    return GatheringEventCriteria.builder()
        .streamKey(streamLeaveKey)
        .eventType(eventType)
        .payload(payload)
        .build();
  }
}
