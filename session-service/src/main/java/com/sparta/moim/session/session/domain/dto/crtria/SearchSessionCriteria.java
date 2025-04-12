package com.sparta.moim.session.session.domain.dto.crtria;

import com.sparta.moim.session.shared.enums.SessionStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchSessionCriteria(
    String title,
    String publisher,
    SessionStatus status,
    String reason,
    String username,
    LocalDateTime openTime,
    LocalDateTime closeTime,

    String role,
    LocalDateTime applyTime,
    LocalDateTime confirmTime,
    Boolean isDeleted,
    Integer page, Integer size, String sort
) {
}
