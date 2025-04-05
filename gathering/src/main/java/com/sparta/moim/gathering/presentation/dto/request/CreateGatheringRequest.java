package com.sparta.moim.gathering.presentation.dto.request;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateGatheringRequest(
    String organizationId,
    String name,
    @PositiveOrZero int count,
    boolean status
) {
  public CreateGatheringCommand toCommand() {
    return new CreateGatheringCommand(organizationId, name,  count, status);
  }
}
