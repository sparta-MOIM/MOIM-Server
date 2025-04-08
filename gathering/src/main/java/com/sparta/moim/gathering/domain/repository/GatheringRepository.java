package com.sparta.moim.gathering.domain.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GatheringRepository {
  Gathering save(Gathering gathering);
  Optional<Gathering> findById(UUID id);
  //TODO:임시 쿼리DSL적용시제거
  List<Gathering> findAll();
}
