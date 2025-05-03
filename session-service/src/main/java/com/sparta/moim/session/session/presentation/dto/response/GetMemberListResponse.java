package com.sparta.moim.session.session.presentation.dto.response;

import com.sparta.moim.session.session.application.dto.result.GetMemberListResult;
import java.util.UUID;

public record GetMemberListResponse(
    UUID id,
    String type
) {
  public GetMemberListResponse(GetMemberListResult member) {
    this(member.id(), member.type());
  }
}
