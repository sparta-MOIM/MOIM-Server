package com.sparta.moim.gathering.gathering.application.dto.command.event;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.GatheringEventCriteria;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.shared.enums.EventType;
import com.sparta.moim.gathering.shared.enums.MemberType;
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

  public GatheringEventCriteria toEventCriteria(String streamKey,
                                                EventType eventType,
                                                String payload) {
    return GatheringEventCriteria.builder()
        .streamKey(streamKey)
        .eventType(eventType)
        .payload(payload)
        .build();
  }
}
