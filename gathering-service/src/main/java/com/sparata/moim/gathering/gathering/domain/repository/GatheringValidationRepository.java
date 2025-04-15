package com.sparata.moim.gathering.gathering.domain.repository;

import java.util.UUID;

public interface GatheringValidationRepository {
  boolean existsByTrackingIdAndDeletedAtNull(UUID trackingId);

  boolean findByGatheringStatus(UUID trackingId);

}
