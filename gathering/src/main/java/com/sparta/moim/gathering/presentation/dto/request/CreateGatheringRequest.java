package com.sparta.moim.gathering.presentation.dto.request;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import jakarta.validation.constraints.Positive;

public record CreateGatheringRequest(
    String organizationId,
    String name,
    String owner,
    @Positive int count,
    boolean status
) {
  public CreateGatheringCommand toCommand() {
    return new CreateGatheringCommand(organizationId, name, owner, count, status);
  }
}
