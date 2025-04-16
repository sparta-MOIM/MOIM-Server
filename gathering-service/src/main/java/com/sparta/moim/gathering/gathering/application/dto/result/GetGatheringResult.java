package com.sparta.moim.gathering.gathering.application.dto.result;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetGatheringResult(
    UUID gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status,
    List<GetGatheringMemberListResult> members,
    LocalDateTime createAt,
    String createBy,
    LocalDateTime updateAt,
    String updateBy
) {
  public static GetGatheringResult get(Gathering gathering, List<GetGatheringMemberListResult> members) {
    return new GetGatheringResult(
        gathering.getTrackingId(),
        gathering.getOrganizationId(),
        gathering.getName(),
        gathering.getOwner(),
        gathering.getCount(),
        gathering.getStatus(),
        members,
        gathering.getCreatedAt(),
        gathering.getCreatedBy(),
        gathering.getModifiedAt(),
        gathering.getModifiedBy()
    );
  }
}
