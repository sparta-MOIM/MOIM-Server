package com.sparta.moim.gathering.gathering.application.event.publisher;

import java.util.UUID;

public interface MemberPublisher {
  void add(UUID gatheringId, UUID memberName);

  void revoke(UUID id, UUID owner);
}
