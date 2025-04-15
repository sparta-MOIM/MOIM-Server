package com.sparata.moim.gathering.gathering.infrastructure.repository;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import com.sparata.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaGatheringValidationRepository extends JpaRepository<Gathering,Long>, GatheringValidationRepository {

  @Query("select g.status from Gathering g where g.trackingId = :trackingId and g.deletedAt is null ")
  boolean isGatheringOpen(UUID trackingId);

}
