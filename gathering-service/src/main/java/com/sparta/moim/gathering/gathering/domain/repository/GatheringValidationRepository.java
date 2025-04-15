package com.sparta.moim.gathering.gathering.domain.repository;

import java.util.UUID;

public interface GatheringValidationRepository {
  /**
 * 지정된 trackingId를 가진 모임이 존재하며 삭제되지 않았는지 확인합니다.
 *
 * @param trackingId 확인할 모임의 추적 ID
 * @return 모임이 존재하고 삭제되지 않은 경우 true, 그렇지 않으면 false
 */
boolean existsByTrackingIdAndDeletedAtNull(UUID trackingId);

  /**
 * 지정된 트래킹 ID를 가진 모임이 현재 오픈 상태인지 여부를 반환합니다.
 *
 * @param trackingId 모임을 식별하는 트래킹 ID
 * @return 모임이 오픈 상태이면 true, 아니면 false
 */
boolean isGatheringOpen(UUID trackingId);

}
