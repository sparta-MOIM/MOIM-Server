package com.moim.schedule.application.command;

import java.time.LocalDateTime;
import java.util.Optional;

public record UpdateScheduleCommand(
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end
) {
}