package com.sparta.moim.member.application.dto.command;

import java.util.List;
import java.util.UUID;

public record RemoveGatheringCommand(
    UUID gatheringId,
    List<String> users
) {
}
