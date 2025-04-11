package com.sparta.moim.session.member.presentation.dto.response;

import com.sparta.moim.session.member.application.dto.result.GetMemberResult;
import java.util.List;

public record GetMemberResponse(
    List<GetMemberListResponse> member
) {
  public static GetMemberResponse get(GetMemberResult result) {
    return new GetMemberResponse(result.member().stream().map(GetMemberListResponse::new)
        .toList());
  }
}
