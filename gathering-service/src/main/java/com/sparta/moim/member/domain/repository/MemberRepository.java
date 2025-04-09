package com.sparta.moim.member.domain.repository;

import com.sparta.moim.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {
  Member save(Member member);
  void deleteByGatheringIdAndMemberId(String gatheringId, String memberId);
}
