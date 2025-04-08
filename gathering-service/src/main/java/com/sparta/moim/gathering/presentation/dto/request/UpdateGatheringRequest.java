package com.sparta.moim.gathering.presentation.dto.request;


import com.sparta.moim.gathering.application.dto.command.UpdateGatheringCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;

public record UpdateGatheringRequest(
    @NotNull String name,
    @PositiveOrZero int count,
    @NotNull Boolean status
) {
  public UpdateGatheringCommand toCommand(Long gatheringId) {
    return new UpdateGatheringCommand(gatheringId, name, count, status);
  }
}
