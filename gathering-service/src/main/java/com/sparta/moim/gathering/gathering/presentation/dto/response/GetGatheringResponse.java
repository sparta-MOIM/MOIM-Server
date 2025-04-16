package com.sparta.moim.gathering.gathering.presentation.dto.response;

import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetGatheringResponse(
    UUID gatheringId,
    String organizationId,
    String name,
    String owner,
    int count,
    boolean status,
    List<GetGatheringMemberListResponse> member,
    LocalDateTime createAt,
    String createBy,
    LocalDateTime updateAt,
    String updateBy
) {
  public static GetGatheringResponse get(GetGatheringResult result) {
    return new GetGatheringResponse(result.gatheringId(),
        result.organizationId(),
        result.name(),
        result.owner(),
        result.count(),
        result.status(),
        result.members()
            .stream()
            .map(g ->
                new GetGatheringMemberListResponse(g.name(), g.type())).toList(),

        result.createAt(),
        result.createBy(),
        result.updateAt(),
        result.updateBy()
    );
  }
}
