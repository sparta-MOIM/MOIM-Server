package com.moim.schedule.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateScheduleRequest(
    @NotNull UUID organizationId,
    @NotNull String title,
    @NotNull String content,
    @NotNull LocalDateTime start,
    @NotNull LocalDateTime end
) {
}
