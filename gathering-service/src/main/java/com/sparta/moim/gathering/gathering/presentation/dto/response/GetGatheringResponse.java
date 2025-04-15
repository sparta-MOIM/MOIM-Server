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
  /**
   * 주어진 GetGatheringResult 객체를 기반으로 GetGatheringResponse 인스턴스를 생성합니다.
   *
   * @param result 조회된 모임 정보를 담고 있는 결과 객체
   * @return 변환된 GetGatheringResponse 객체
   */
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
