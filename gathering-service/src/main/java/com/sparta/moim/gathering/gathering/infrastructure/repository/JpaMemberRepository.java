package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

  @Modifying
  @Query("delete from Member m where m.gatheringId = :gatheringId and m.memberId IN (:memberIds)")
  void deleteAllByGatheringIdAndMembers(@Param("gatheringId") UUID gatheringId,
                                        @Param("memberIds") List<String> memberIds);

  @Query("select m from Member m where m.gatheringId = :gatheringId")
  List<Member> findMembers(@Param("gatheringId") UUID gatheringId);

  @Query("select m.type from Member m where m.gatheringId = :gatheringId and m.memberId = :memberId")
  MemberType findMemberType(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);

  @Query("""
      select m from Member m where m.gatheringId = :gatheringId  and m.memberId <> :memberId
      """)
  Optional<Member> findByMemberStatusOwner(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);
}
