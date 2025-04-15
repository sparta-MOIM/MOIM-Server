package com.sparta.moim.gathering.gathering.application.event.publisher;

import java.util.UUID;

public interface AddMemberPublisher {
  void add(UUID gatheringId, String memberName);
}
