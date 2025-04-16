package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.enums.MemberType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.query.Param;

public interface MemberRepository {
  Member save(Member member);

  void deleteByGatheringIdAndMemberId(UUID gatheringId, String memberId);

  void deleteAllByGatheringIdAndMembers(UUID gatheringId, List<String> memberIds);

  List<Member> findMembers(UUID gatheringId);

  boolean existsByMemberId(String memberId);

  MemberType findMemberType(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);

  Optional<Member> findByMemberStatusOwner(UUID gatheringId, String memberId);
}
