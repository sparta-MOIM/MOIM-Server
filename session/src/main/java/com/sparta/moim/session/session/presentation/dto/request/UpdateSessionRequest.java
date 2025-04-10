package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateSessionRequest(
    String title,
    int count
) {
  public UpdateSessionCommand toCommand(UUID sessionId) {
    return UpdateSessionCommand.builder()
        .sessionId(sessionId)
        .title(title)
        .count(count).build();
  }
}
