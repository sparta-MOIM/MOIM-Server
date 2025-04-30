package com.sparta.moim.session.member.presentation.dto.response;

import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;
import java.util.UUID;

public record GetMemberListResponse(
    UUID id,
    String type
) {
  public GetMemberListResponse(GetMemberListResult member) {
    this(member.id(), member.type());
  }
}
