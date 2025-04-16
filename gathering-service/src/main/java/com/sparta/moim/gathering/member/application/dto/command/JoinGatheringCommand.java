package com.sparta.moim.gathering.member.application.dto.command;

import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.enums.MemberType;
import com.sparta.moim.common.security.CustomUserDetails;
import java.time.LocalDateTime;
import java.util.UUID;

public record JoinGatheringCommand(UUID gatheringId, String username) {

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
