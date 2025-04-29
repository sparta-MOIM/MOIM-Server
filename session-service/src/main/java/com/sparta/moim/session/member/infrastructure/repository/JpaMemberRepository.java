package com.sparta.moim.session.member.infrastructure.repository;

import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {


  @Modifying
  @Query("DELETE FROM Member m where m.sessionId =:sessionId and m.memberId =:memberId and m.type <> 'PUBLISHER'")
  void deleteMemberBySessionId(UUID sessionId, UUID memberId);
  @Modifying
  @Query("delete from Member m where m.sessionId = :id and m.memberId IN (:members) and m.type <> 'PUBLISHER'")
  void removeMembers(UUID id, List<String> members);


  @Query("""
        select count(m) from Member m where m.sessionId = :sessionId and m.memberId IN (:members) and m.type <> "PUBLISHER"
        """)
  long countMembersUnpublishable(UUID sessionId, List<String> members);
}
