package com.sparta.moim.gathering.application.dto.command;

import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;

public record DeleteGatheringCommand(UUID gatheringId,
                                     String username,
                                     String role,
                                     UUID id
) {
  public DeleteGatheringCommand(UUID gatheringId, CustomUserDetails details) {
    this(gatheringId, details.getUsername(), details.getRole(), details.getTrackingId());
  }
}
