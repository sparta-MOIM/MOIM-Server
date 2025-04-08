package com.sparta.moim.gathering.infrastructure.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.domain.repository.GatheringRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaGatheringRepository extends JpaRepository<Gathering, Long>, GatheringRepository {
  @Query("SELECT COUNT(g) > 0 FROM Gathering g WHERE g.name = :name AND g.deletedBy IS NULL AND g.trackingId <> :id")
  boolean existsByNameAndDeletedByIsNullAndIdNot(@Param("name") String name, @Param("id") UUID id);
}
