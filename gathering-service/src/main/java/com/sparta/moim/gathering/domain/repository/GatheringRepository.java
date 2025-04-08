package com.sparta.moim.gathering.domain.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.util.Optional;
import java.util.UUID;

public interface GatheringRepository {
  Gathering save(Gathering gathering);
  Optional<Gathering> findByTrackingIdAndDeletedByIsNull(UUID id);
  boolean existsByNameAndDeletedByIsNull(String name);
  boolean existsByNameAndDeletedByIsNullAndIdNot(String name, UUID id);
}
