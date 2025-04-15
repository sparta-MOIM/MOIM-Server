package com.sparta.moim.gathering.gathering.domain.repository;

import java.util.UUID;

public interface GatheringValidationRepository {
  boolean existsByTrackingIdAndDeletedAtNull(UUID trackingId);

  boolean isGatheringOpen(UUID trackingId);

}
