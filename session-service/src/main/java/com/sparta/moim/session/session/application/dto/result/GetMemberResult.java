package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.session.domain.entity.Member;
import java.util.List;

public record GetMemberResult(
    List<GetMemberListResult> member
) {
  public static GetMemberResult get(List<Member> memberList) {
    if (memberList == null) {
      return new GetMemberResult(List.of());
    }
    return new GetMemberResult(memberList.stream().map(GetMemberListResult::new).toList());
  }
}
