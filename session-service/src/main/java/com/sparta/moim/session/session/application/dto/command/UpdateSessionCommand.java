package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.entity.Session;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateSessionCommand(UUID sessionId,
                                   String title,
                                   int totalCount) {
  public Session toDomain() {
    return Session.builder()
        .trackingId(sessionId)
        .title(title)
        .totalCount(totalCount)
        .build();
  }
}
