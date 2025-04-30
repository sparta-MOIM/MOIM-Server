package com.sparta.moim.gathering.gathering.application.dto.command;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateGatheringCommand(
    String organizationId,
    String name,
    UUID owner,
    int count,
    boolean status
) {

  public Gathering toEntity() {
    return Gathering.create(
        organizationId,
        name,
        owner,
        count,
        status

    );
  }
}
