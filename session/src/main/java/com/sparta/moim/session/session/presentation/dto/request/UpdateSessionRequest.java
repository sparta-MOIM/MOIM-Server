package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import java.util.UUID;

public record UpdateSessionRequest(
    String title,
    int count,
    String status
) {
  public UpdateSessionCommand toCommand(UUID sessionId) {
    return UpdateSessionCommand.builder()
        .sessionId(sessionId)
        .title(title)
        .count(count)
        .status(status).build();
  }
}
