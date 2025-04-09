package com.sparta.moim.member.presentation.dto.request;

import com.sparta.moim.member.application.dto.command.RemoveGatheringCommand;
import java.util.List;
import java.util.UUID;

public record RemoveGatheringRequest(
    List<String> users
) {
  public RemoveGatheringCommand toCommand(UUID gatheringId) {
    return new RemoveGatheringCommand(gatheringId, users);
  }
}
