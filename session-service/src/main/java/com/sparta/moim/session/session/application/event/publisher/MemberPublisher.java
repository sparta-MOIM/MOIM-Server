package com.sparta.moim.session.session.application.event.publisher;

import java.util.UUID;

public interface MemberPublisher {
  void add(UUID sessionId, String memberName);
}
