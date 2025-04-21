package com.sparta.moim.gathering.gathering.application.dto.command;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchGatheringCommand(
    String name,
    boolean status,
    Boolean isDeleted,
    LocalDateTime startTime,
    LocalDateTime endTime,
    // 계정 정보
    String username,
    UUID userId,
    String role,
    Integer page, Integer size, String sort
) {
  public SearchGatheringCriteria toCriteria() {
    return SearchGatheringCriteria.builder()
        .name(name)
        .status(status)
        .isDeleted(isDeleted)
        .startTime(startTime)
        .endTime(endTime)
        .username(username)
        .userId(userId)
        .role(role)
        .page(page)
        .size(size)
        .sort(sort)
        .build();
  }

  public static record JoinGatheringCommand(UUID gatheringId, String username) {

    public JoinGatheringCommand(UUID gatheringId, CustomUserDetails userInfo) {
      this(gatheringId, userInfo.getUsername());
    }

    public Member toDomain() {
      return Member.builder()
          .gatheringId(gatheringId)
          .memberId(username)
          .type(MemberType.GENERAL)
          .joinTime(LocalDateTime.now())
          .build();
    }
  }

  public static record LeaveGatheringCommand(UUID gatheringId, String username) {
    public LeaveGatheringCommand(UUID gatheringId, CustomUserDetails userInfo) {
      this(gatheringId, userInfo.getUsername());
    }
  }

  public static record RemoveGatheringCommand(
      UUID gatheringId,
      List<String> users,
      String memberId
  ) {
  }
}
