package com.sparata.moim.gathering.member.presentation.dto.request;

import com.sparata.moim.gathering.member.application.dto.command.RemoveGatheringCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record RemoveGatheringRequest(
    @NotNull
    @Size(min = 1, message = "At least one user must be specified")
    List<@NotBlank String> users
) {
  public RemoveGatheringCommand toCommand(UUID gatheringId, String memberId) {
    return new RemoveGatheringCommand(gatheringId, users, memberId);
  }
}
