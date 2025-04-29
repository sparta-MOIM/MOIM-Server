package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import java.util.UUID;

public record GetSessionMemberListResponse(
    UUID id,
    String type
) {
  public GetSessionMemberListResponse(GetSessionMemberListResult member) {
    this(member.id(), member.type());
  }
}
