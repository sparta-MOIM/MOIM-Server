package com.moim.schedule.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleResponse(
    UUID id,
    UUID organizationId,
    String title,
    String content,
    LocalDateTime start,
    LocalDateTime end
) {
}
