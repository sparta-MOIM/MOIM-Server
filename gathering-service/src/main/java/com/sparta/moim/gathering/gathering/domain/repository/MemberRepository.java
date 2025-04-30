package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
  Member save(Member member);

  void deleteByGatheringIdAndMemberId(UUID gatheringId, UUID memberId);

  void deleteAllByGatheringIdAndMembers(UUID gatheringId, List<UUID> memberIds);

  List<Member> findMembers(UUID gatheringId);

  boolean existsByGatheringIdAndMemberId(UUID gatheringId, UUID memberId);

  MemberType findMemberType(UUID gatheringId, UUID memberId);

  Optional<Member> findByMemberStatusOwner(UUID gatheringId, UUID memberId);

  Optional<Member> existsOwner(UUID gatheringId);
}
