package com.sparta.moim.gathering.gathering.application.dto.result;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import java.util.UUID;

public record GetGatheringMemberListResult(
    UUID name,
    String type
) {
  public GetGatheringMemberListResult(Member member) {
    this(member.getMemberId(), member.getType().name());
  }
}
