package com.sparta.moim.session.member.application.event.publisher;

import java.util.UUID;

public interface HandleSessionMemberCountPublisher {
  void increase(UUID sessionId, String memberId);
  void decrease(UUID sessionId, String memberId);
  void remove(UUID sessionId, long count);
}
