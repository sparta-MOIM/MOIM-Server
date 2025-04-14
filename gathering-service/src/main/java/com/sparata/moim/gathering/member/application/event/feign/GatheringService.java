package com.sparata.moim.gathering.member.application.event.feign;

import java.util.UUID;

public interface GatheringService {
  void isExitsGathering(UUID gatheringId);
}
