package com.sparta.moim.gathering.gathering.presentation.dto.request;

import com.sparta.moim.gathering.gathering.application.dto.command.CreateGatheringCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateGatheringRequest(
    @NotNull String organizationId,
    @NotNull String name,
    @NotNull String owner,
    @Positive int count,
    @Nullable Boolean status
) {
  public CreateGatheringCommand toCommand() {
    return new CreateGatheringCommand(organizationId, name, owner, count, status == null || status);
  }
}
