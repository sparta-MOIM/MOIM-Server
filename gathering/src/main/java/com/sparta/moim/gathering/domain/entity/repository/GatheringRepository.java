package com.sparta.moim.gathering.domain.entity.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.util.Optional;
import java.util.UUID;

public interface GatheringRepository {
  Gathering save(Gathering gathering);
  Optional<Gathering> findById(UUID id);
}
