package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaGatheringRepository extends JpaRepository<Gathering, Long>, GatheringRepository {

  @Query("""
        SELECT CAST(g.organizationId AS java.util.UUID) from Gathering g where g.trackingId = :gatheringId
        """)
  UUID findOrganizationId(@Param("gatheringId") UUID gatheringId);
}
