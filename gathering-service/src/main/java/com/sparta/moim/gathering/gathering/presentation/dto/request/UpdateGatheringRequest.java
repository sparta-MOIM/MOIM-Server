package com.sparta.moim.gathering.gathering.presentation.dto.request;


import com.sparta.moim.gathering.gathering.application.dto.command.UpdateGatheringCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;

public record UpdateGatheringRequest(
    @Nullable String name,
    @PositiveOrZero int count,
    @Nullable Boolean status
) {
  public UpdateGatheringCommand toCommand(UUID gatheringId) {
    return new UpdateGatheringCommand(gatheringId, name, count, status);
  }
}
