package com.sparta.moim.gathering.gathering.presentation.dto.request;

import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record RemoveGatheringRequest(
    @NotNull
    @Size(min = 1, message = "At least one user must be specified")
    List<UUID> users
) {
  public RemoveGatheringCommand toCommand(UUID gatheringId, UUID memberId) {
    return new RemoveGatheringCommand(gatheringId, users, memberId);
  }
}
