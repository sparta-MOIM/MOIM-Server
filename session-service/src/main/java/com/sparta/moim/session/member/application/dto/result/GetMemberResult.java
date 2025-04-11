package com.sparta.moim.session.member.application.dto.result;

import com.sparta.moim.session.member.domain.entity.Member;
import java.util.List;

public record GetMemberResult(
    List<GetMemberListResult> member
) {
  public static GetMemberResult get(List<Member> memberList) {
    return new GetMemberResult(memberList.stream().map(GetMemberListResult::new).toList());
  }
}
