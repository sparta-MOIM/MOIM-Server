package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Builder;

@Builder
public record  UpdateSessionRequest(
    @Nullable String title,
    @PositiveOrZero int count
) {
  public UpdateSessionCommand toCommand(UUID sessionId) {
    return UpdateSessionCommand.builder()
        .sessionId(sessionId)
        .title(title)
        .totalCount(count).build();
  }
}
