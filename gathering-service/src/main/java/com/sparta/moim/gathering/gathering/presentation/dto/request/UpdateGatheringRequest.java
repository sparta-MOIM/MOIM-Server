package com.sparta.moim.gathering.gathering.presentation.dto.request;


import com.sparta.moim.gathering.gathering.application.dto.command.UpdateGatheringCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateGatheringRequest(
    @Nullable String name,
    @Nullable String owner,
    @Positive int count,
    @Nullable Boolean status
) {
  public UpdateGatheringCommand toCommand(UUID gatheringId) {
    return new UpdateGatheringCommand(gatheringId, owner, name, count, status);
  }
}
