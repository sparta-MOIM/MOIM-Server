package com.sparta.moim.gathering.gathering.application.dto.command.event;

import java.util.List;
import java.util.UUID;

public record RemoveGatheringCommand(
    UUID gatheringId,
    List<String> users,
    String memberId
) {
}
