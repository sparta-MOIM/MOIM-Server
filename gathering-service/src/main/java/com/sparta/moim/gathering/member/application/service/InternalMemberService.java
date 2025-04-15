package com.sparta.moim.gathering.member.application.service;

import com.sparta.moim.gathering.member.application.dto.result.GetMemberListResult;
import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalMemberService {
  private final MemberRepository memberRepository;

  /**
   * 지정된 모임 ID에 속한 모든 멤버 정보를 조회하여 DTO 리스트로 반환합니다.
   *
   * @param gatheringId 멤버를 조회할 모임의 ID
   * @return 모임에 속한 멤버들의 ID와 멤버 유형 이름을 담은 결과 리스트
   */
  public List<GetMemberListResult> findMembers(UUID gatheringId) {
    List<Member> members = memberRepository.findMembers(gatheringId);
    return findMembers(members);
  }

  /**
   * Member 엔티티 목록을 GetMemberListResult DTO 목록으로 변환합니다.
   *
   * @param members 변환할 Member 엔티티 리스트
   * @return 각 멤버의 ID와 타입 이름을 포함하는 GetMemberListResult 리스트
   */
  private static List<GetMemberListResult> findMembers(List<Member> members) {
    return members.stream().map(g -> new GetMemberListResult(g.getMemberId(), g.getType().name()))
        .collect(Collectors.toList());
  }
}
