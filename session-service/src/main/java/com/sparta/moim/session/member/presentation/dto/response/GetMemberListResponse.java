package com.sparta.moim.session.member.presentation.dto.response;

import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;

public record GetMemberListResponse(
    String memberName,
    String type
) {
  public GetMemberListResponse(GetMemberListResult member) {
    this(member.name(), member.type());
  }
}
