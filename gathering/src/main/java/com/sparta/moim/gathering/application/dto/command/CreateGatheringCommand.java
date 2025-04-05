package com.sparta.moim.gathering.application.dto.command;

import com.sparta.moim.gathering.domain.entity.Gathering;

public record CreateGatheringCommand(
    String organizationId,
    String name,
    int count,
    boolean status
) {

  public Gathering toEntity() {
    return Gathering.create(
        organizationId,
        name,
        "생성아이디",
        count,
        status

    );
  }
}
