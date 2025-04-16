package com.sparta.moim.gathering.gathering.application.event.publisher;

import java.util.UUID;

public interface MemberPublisher {
  void add(UUID gatheringId, String memberName);

  void revoke(UUID id, String owner);
}
