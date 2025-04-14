package com.sparata.moim.gathering.member.repository;

import com.sparata.moim.gathering.member.domain.Member;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

  @Modifying
  @Query("delete from Member m where m.gatheringId = :gatheringId and m.memberId IN (:memberIds)")
  void deleteAllByGatheringIdAndMembers(@Param("gatheringId") String gatheringId, @Param("memberIds") List<String> memberIds);
}
