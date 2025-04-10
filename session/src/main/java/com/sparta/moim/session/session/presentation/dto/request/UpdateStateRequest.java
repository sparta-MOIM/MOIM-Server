package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.UpdateStateStateCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateStateRequest(
    @NotNull String status
) {
  public UpdateStateStateCommand toCommand(UUID sessionId) {
    return new UpdateStateStateCommand(sessionId, status);
  }
}
