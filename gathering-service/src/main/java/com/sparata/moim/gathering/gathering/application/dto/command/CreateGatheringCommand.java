package com.sparata.moim.gathering.gathering.application.dto.command;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import lombok.Builder;

@Builder
public record CreateGatheringCommand(
    String organizationId,
    String name,
    String owner,
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
