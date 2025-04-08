package com.sparta.moim.gathering.application.dto.command;

import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;

public record DeleteGatheringCommand(Long gatheringId,
                                     String username,
                                     String role,
                                     UUID id
) {
  public DeleteGatheringCommand(Long gatheringId, CustomUserDetails details) {
    this(gatheringId, details.getUsername(), details.getRole(), details.getId());
  }
}
