package com.sparta.moim.member.application.dto.command;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.member.domain.Member;
import java.time.LocalDateTime;
import java.util.UUID;

public record JoinGatheringCommand(UUID gatheringId, String username) {

  public JoinGatheringCommand(UUID gatheringId, CustomUserDetails userInfo) {
    this(gatheringId, userInfo.getUsername());
  }

  public Member toDomain() {
    return Member.builder()
        .gatheringId(gatheringId.toString())
        .memberId(username)
        .joinTime(LocalDateTime.now())
        .build();
  }
}
