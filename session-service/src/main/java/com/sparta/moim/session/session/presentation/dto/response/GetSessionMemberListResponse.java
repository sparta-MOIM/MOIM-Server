package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;

public record GetSessionMemberListResponse(
    String name,
    String type
) {
  public GetSessionMemberListResponse(GetSessionMemberListResult member) {
    this(member.memberName(), member.type());
  }
}
