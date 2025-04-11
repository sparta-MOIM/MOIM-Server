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
  @Query("delete from Member m where m.sessionId = :id and m.memberName IN (:members) and m.type <> 'PUBLISHER'")
  void removeMembers(UUID id, List<String> members);
}
