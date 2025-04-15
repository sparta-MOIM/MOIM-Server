package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import java.util.Optional;
import java.util.UUID;

public interface GatheringRepository {
  /**
 * 새로운 Gathering 엔티티를 저장하거나 기존 엔티티를 갱신합니다.
 *
 * @param gathering 저장할 Gathering 엔티티
 * @return 저장된 Gathering 엔티티 인스턴스
 */
Gathering save(Gathering gathering);

  /**
 * 주어진 trackingId로 삭제되지 않은 Gathering 엔티티를 조회합니다.
 *
 * @param id 조회할 Gathering의 trackingId
 * @return 삭제되지 않은 Gathering이 존재하면 Optional에 담아 반환하며, 없으면 빈 Optional을 반환합니다.
 */
Optional<Gathering> findByTrackingIdAndDeletedAtIsNull(UUID id);

  /**
 * 삭제되지 않은 상태에서 지정한 이름을 가진 Gathering 엔티티가 존재하는지 확인합니다.
 *
 * @param name 조회할 Gathering의 이름
 * @return 해당 이름의 Gathering이 삭제되지 않은 상태로 존재하면 true, 아니면 false
 */
boolean existsByNameAndDeletedAtIsNull(String name);

  /****
 * 지정한 이름을 가진 모임이 존재하는지 확인하되, 삭제되지 않았으며 주어진 trackingId와 다른 모임만을 대상으로 합니다.
 *
 * @param name      확인할 모임 이름
 * @param trackingId 제외할 모임의 trackingId
 * @return 조건을 만족하는 모임이 존재하면 true, 아니면 false
 */
boolean existsByNameAndDeletedAtIsNullAndTrackingIdNot(String name, UUID trackingId);
}
