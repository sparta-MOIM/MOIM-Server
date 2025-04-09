package com.sparta.moim.session.session.application.dto.command;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateSessionCommand(UUID sessionId,
                                   String title,
                                   int count,
                                   String status) {
  public Session toDomain() {
    return Session.builder()
        .trackingId(sessionId)
        .title(title)
        .count(count)
        .status(SessionStatus.valueOf(status))
        .build();
  }
}
