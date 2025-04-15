package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaGatheringValidationRepository extends JpaRepository<Gathering,Long>, GatheringValidationRepository {

  /**
   * 주어진 trackingId에 해당하는 모임이 삭제되지 않았고, 현재 열려 있는지 여부를 반환합니다.
   *
   * @param trackingId 모임을 식별하는 UUID
   * @return 모임이 열려 있으면 true, 그렇지 않으면 false
   */
  @Query("select g.status from Gathering g where g.trackingId = :trackingId and g.deletedAt is null ")
  boolean isGatheringOpen(UUID trackingId);

}
