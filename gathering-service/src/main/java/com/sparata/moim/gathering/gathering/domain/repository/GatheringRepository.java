package com.sparata.moim.gathering.gathering.domain.repository;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import java.util.Optional;
import java.util.UUID;

public interface GatheringRepository {
  Gathering save(Gathering gathering);
  Optional<Gathering> findByTrackingId(UUID id);
}
