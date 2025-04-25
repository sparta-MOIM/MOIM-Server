package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaGatheringRepository extends JpaRepository<Gathering, Long>, GatheringRepository {

  @Query("""
        SELECT g.organizationId from Gathering g where g.trackingId = :gatheringId
        """)
  String findOrganizationId(@Param("gatheringId") UUID gatheringId);
}
