package com.sparta.moim.gathering.presentation.dto.request;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record CreateGatheringRequest(
    @NotNull String organizationId,
    @NotNull String name,
    @NotNull String owner,
    @Positive int count,
    @DefaultValue(value = "true") boolean status
) {
  public CreateGatheringCommand toCommand() {
    return new CreateGatheringCommand(organizationId, name, owner, count, status);
  }
}
