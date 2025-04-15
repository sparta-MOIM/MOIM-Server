package com.sparta.moim.gathering.gathering.application.dto.command;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.util.UUID;

public record UpdateGatheringCommand(
    UUID gatheringId,
    String name,
    int count,
    Boolean status) {


  public Gathering toDomain() {
    return Gathering.update(
        gatheringId,
        name,
        count,
        status
    );
  }
}
