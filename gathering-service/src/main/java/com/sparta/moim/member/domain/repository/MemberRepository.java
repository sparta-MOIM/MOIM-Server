package com.sparta.moim.member.domain.repository;

import com.sparta.moim.member.domain.Member;
import java.util.List;

public interface MemberRepository {
  Member save(Member member);
  void deleteByGatheringIdAndMemberId(String gatheringId, String memberId);
  void deleteAllByGatheringIdAndMembers(String gatheringId, List<String> memberIds);
}
