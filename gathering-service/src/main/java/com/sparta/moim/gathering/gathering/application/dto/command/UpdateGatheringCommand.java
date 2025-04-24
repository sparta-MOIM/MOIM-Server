package com.sparta.moim.gathering.gathering.application.dto.command;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateGatheringCommand(
    UUID gatheringId,
    UUID owner,
    String name,
    int count,
    Boolean status) {


  public Gathering toDomain() {
    return Gathering.update(
        gatheringId,
        owner,
        name,
        count,
        status
    );
  }
}
