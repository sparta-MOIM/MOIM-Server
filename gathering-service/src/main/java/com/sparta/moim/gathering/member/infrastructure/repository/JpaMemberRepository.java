package com.sparta.moim.gathering.member.infrastructure.repository;

import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.enums.MemberType;
import com.sparta.moim.gathering.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

  /**
   * 지정된 모임 ID와 회원 ID 목록에 해당하는 모든 회원 엔티티를 삭제합니다.
   *
   * @param gatheringId 삭제할 회원들이 속한 모임의 ID
   * @param memberIds 삭제할 회원들의 ID 목록
   */
  @Modifying
  @Query("delete from Member m where m.gatheringId = :gatheringId and m.memberId IN (:memberIds)")
  void deleteAllByGatheringIdAndMembers(@Param("gatheringId") UUID gatheringId, @Param("memberIds") List<String> memberIds);

  /**
   * 지정된 모임 ID에 속한 모든 멤버 엔티티 목록을 조회합니다.
   *
   * @param gatheringId 멤버를 조회할 모임의 ID
   * @return 해당 모임에 속한 멤버 엔티티 리스트
   */
  @Query("select m from Member m where m.gatheringId = :gatheringId")
  List<Member> findMembers(@Param("gatheringId") UUID gatheringId);

  /**
   * 지정된 모임 ID와 멤버 ID에 해당하는 멤버의 타입을 반환합니다.
   *
   * @param gatheringId 멤버가 속한 모임의 ID
   * @param memberId 조회할 멤버의 ID
   * @return 해당 멤버의 MemberType, 존재하지 않으면 null 반환
   */
  @Query("select m.type from Member m where m.gatheringId = :gatheringId and m.memberId = :memberId")
  MemberType findMemberType(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);
}
