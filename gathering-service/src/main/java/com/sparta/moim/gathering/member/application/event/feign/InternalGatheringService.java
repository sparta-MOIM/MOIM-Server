package com.sparta.moim.gathering.member.application.event.feign;

import java.util.UUID;

public interface InternalGatheringService {
  /**
 * 지정된 모임 ID에 해당하는 모임이 존재하는지 검증합니다.
 *
 * @param gatheringId 존재 여부를 확인할 모임의 고유 식별자
 */
void validateGatheringExists(UUID gatheringId);
  /**
 * 주어진 모임이 '오픈' 상태인지 유효성을 검사합니다.
 *
 * @param gatheringId 검사할 모임의 고유 식별자
 */
void validateGatheringStatusOpen(UUID gatheringId);
}
