package com.sparta.moim.gathering.member.application.event.feign;

import java.util.UUID;

public interface InternalGatheringService {
  void validateGatheringExists(UUID gatheringId);
  void validateGatheringStatusOpen(UUID gatheringId);
}
