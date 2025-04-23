package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
  Member save(Member member);

  void deleteByGatheringIdAndMemberId(UUID gatheringId, String memberId);

  void deleteAllByGatheringIdAndMembers(UUID gatheringId, List<String> memberIds);

  List<Member> findMembers(UUID gatheringId);

  boolean existsByGatheringIdAndMemberId(UUID gatheringId, String memberId);

  MemberType findMemberType(UUID gatheringId, String memberId);

  Optional<Member> findByMemberStatusOwner(UUID gatheringId, String memberId);

  Optional<Member> existsOwner(UUID gatheringId);
}
