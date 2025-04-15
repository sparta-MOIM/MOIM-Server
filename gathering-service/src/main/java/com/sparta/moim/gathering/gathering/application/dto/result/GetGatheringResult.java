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
  /**
   * Gathering 엔티티와 멤버 목록을 기반으로 GetGatheringResult 인스턴스를 생성합니다.
   *
   * @param gathering 정보를 추출할 Gathering 엔티티
   * @param members 해당 모임의 멤버 정보 목록
   * @return Gathering 및 멤버 정보를 포함하는 GetGatheringResult 객체
   */
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
