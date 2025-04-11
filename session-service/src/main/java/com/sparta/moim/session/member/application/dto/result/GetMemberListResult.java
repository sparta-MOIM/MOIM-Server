package com.sparta.moim.session.member.application.dto.result;

import com.sparta.moim.session.member.domain.entity.Member;

public record GetMemberListResult(
    String name,
    String type
) {

  public GetMemberListResult(Member member) {
    this(member.getMemberName(), member.getType().name());
  }
}
