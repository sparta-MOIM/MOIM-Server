package com.sparata.moim.gathering.member.application.service;

import com.sparata.moim.gathering.member.application.dto.result.GetMemberListResult;
import com.sparata.moim.gathering.member.domain.Member;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalMemberService {
  private final MemberRepository memberRepository;

  public List<GetMemberListResult> findMembers(UUID gatheringId) {
    List<Member> members = memberRepository.findMembers(gatheringId);
    return findMembers(members);
  }

  private static List<GetMemberListResult> findMembers(List<Member> members) {
    return members.stream().map(g -> new GetMemberListResult(g.getMemberId(), g.getType().name()))
        .collect(Collectors.toList());
  }
}
