package com.sparta.moim.session.session.application.event.publisher;

import java.util.UUID;

public interface AddMemberPublisher {
  void add(UUID sessionId, UUID memberId);
}
