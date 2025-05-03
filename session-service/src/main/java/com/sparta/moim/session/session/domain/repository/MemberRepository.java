package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Member;
import java.util.List;
import java.util.UUID;

public interface MemberRepository {
  Member save(Member member);
  void deleteMemberBySessionId(UUID sessionId, UUID memberId);
  void removeMembers(UUID uuid, List<UUID> members);

  List<Member> findAllBySessionId(UUID sessionId);
  boolean existsBySessionIdAndMemberId(UUID sessionId, UUID memberId);

  void deleteAllBySessionId(UUID sessionId);

  long countMembersUnpublishable(UUID uuid, List<UUID> members);
}
