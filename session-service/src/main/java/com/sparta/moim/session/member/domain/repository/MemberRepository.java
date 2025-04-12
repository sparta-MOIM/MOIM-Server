package com.sparta.moim.session.member.domain.repository;

import com.sparta.moim.session.member.domain.entity.Member;
import java.util.List;
import java.util.UUID;

public interface MemberRepository {
  Member save(Member member);
  void deleteMemberBySessionId(UUID sessionId, String memberName);
  void removeMembers(UUID uuid, List<String> members);

  List<Member> findAllBySessionId(UUID sessionId);
  boolean existsBySessionIdAndMemberName(UUID sessionId, String memberName);

  void deleteAllBySessionId(UUID sessionId);
}
