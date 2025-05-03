package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.RemoveMemberCommand;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record RemoveMemberRequest(
    @NotNull List<UUID> members
) {
  public RemoveMemberCommand toCommand(UUID sessionId) {
    return new RemoveMemberCommand(sessionId, members);
  }
}
