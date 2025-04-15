package com.sparata.moim.gathering.member.domain.repository;

import com.sparata.moim.gathering.member.domain.Member;
import java.util.List;
import java.util.UUID;

public interface MemberRepository {
  Member save(Member member);
  void deleteByGatheringIdAndMemberId(UUID gatheringId, String memberId);
  void deleteAllByGatheringIdAndMembers(UUID gatheringId, List<String> memberIds);

  List<Member> findMembers(UUID gatheringId);
}
