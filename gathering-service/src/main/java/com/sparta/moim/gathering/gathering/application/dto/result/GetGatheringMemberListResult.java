package com.sparta.moim.gathering.gathering.application.dto.result;

import com.sparta.moim.gathering.gathering.domain.entity.Member;

public record GetGatheringMemberListResult(
    String name,
    String type
) {
  public GetGatheringMemberListResult(Member member) {
    this(member.getMemberId(), member.getType().name());
  }
}
