package com.sparta.moim.gathering.domain.entity.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;

public interface GatheringRepository {
  Gathering save(Gathering gathering);
}
