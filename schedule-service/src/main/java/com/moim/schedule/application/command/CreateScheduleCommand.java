package com.moim.schedule.application.command;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateScheduleCommand(
    @NotNull UUID organizationId,
    @NotNull String title,
    @NotNull String content,
    @NotNull LocalDateTime start,
    @NotNull LocalDateTime end
) {
}
