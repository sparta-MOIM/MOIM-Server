package com.sparta.moim.gathering.gathering.application.event.publisher;

import java.util.UUID;

public interface AddMemberPublisher {
  /**
 * 지정된 모임에 새로운 멤버 추가 이벤트를 발행합니다.
 *
 * @param gatheringId 멤버를 추가할 모임의 고유 식별자
 * @param memberName 추가할 멤버의 이름
 */
void add(UUID gatheringId, String memberName);
}
