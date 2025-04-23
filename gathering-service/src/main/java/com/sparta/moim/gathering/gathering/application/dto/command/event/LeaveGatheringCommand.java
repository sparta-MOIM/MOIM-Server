package com.sparta.moim.gathering.gathering.application.dto.command.event;

import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;

public record LeaveGatheringCommand(UUID gatheringId, String username) {
  public LeaveGatheringCommand(UUID gatheringId, CustomUserDetails userInfo) {
    this(gatheringId, userInfo.getUsername());
  }
}
