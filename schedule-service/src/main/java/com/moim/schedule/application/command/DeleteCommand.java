package com.moim.schedule.application.command;

import java.util.UUID;

public record DeleteCommand(
    UUID id
) {
}
