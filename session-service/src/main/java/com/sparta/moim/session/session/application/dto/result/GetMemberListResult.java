package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.session.domain.entity.Member;
import java.util.UUID;

public record GetMemberListResult(
    UUID id,
    String type
) {

  public GetMemberListResult(Member member) {
    this(member.getMemberId(), member.getType().name());
  }
}
