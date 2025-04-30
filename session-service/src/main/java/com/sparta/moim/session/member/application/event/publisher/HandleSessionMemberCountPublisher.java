package com.sparta.moim.session.member.application.event.publisher;

import java.util.UUID;

public interface HandleSessionMemberCountPublisher {
  void increase(UUID sessionId, UUID memberId);
  void decrease(UUID sessionId, UUID memberId);
  void remove(UUID sessionId, long count);
}
