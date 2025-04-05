package com.sparta.moim.gathering.presentation.dto.request;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;

public record CreateGatheringRequest(
    String organizationId,
    String name,
    int count,
    boolean status
) {
  public CreateGatheringCommand toCommand() {
    return new CreateGatheringCommand(organizationId, name,  count, status);
  }
}
