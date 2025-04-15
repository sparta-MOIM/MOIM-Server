package com.sparta.moim.gathering.member.application.event.feign;

import java.util.UUID;

public interface InternalGatheringService {
  void isExitsGathering(UUID gatheringId);
  void isGatheringStatusOpen(UUID gatheringId);
}
