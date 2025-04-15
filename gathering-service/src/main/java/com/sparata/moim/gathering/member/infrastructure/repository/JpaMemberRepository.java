package com.sparata.moim.gathering.member.infrastructure.repository;

import com.sparata.moim.gathering.member.domain.Member;
import com.sparata.moim.gathering.member.domain.enums.MemberType;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

  @Modifying
  @Query("delete from Member m where m.gatheringId = :gatheringId and m.memberId IN (:memberIds)")
  void deleteAllByGatheringIdAndMembers(@Param("gatheringId") UUID gatheringId, @Param("memberIds") List<String> memberIds);

  @Query("select m from Member m where m.gatheringId = :gatheringId")
  List<Member> findMembers(@Param("gatheringId") UUID gatheringId);

  @Query("select m.type from Member m where m.gatheringId = :gatheringId and m.memberId = :memberId")
  MemberType findMemberType(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);
}
