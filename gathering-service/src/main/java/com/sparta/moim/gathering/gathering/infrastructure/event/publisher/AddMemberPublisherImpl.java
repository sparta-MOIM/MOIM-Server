package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.event.publisher.AddMemberPublisher;
import com.sparta.moim.gathering.shared.dto.SharedGatheringMember;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddMemberPublisherImpl implements AddMemberPublisher {
  private final ApplicationEventPublisher publisher;

  /**
   * 주어진 모임 ID와 멤버 이름을 사용하여 'ADMIN' 유형의 멤버 추가 이벤트를 발행합니다.
   *
   * @param gatheringId 멤버가 추가될 모임의 고유 식별자
   * @param memberName 추가될 멤버의 이름
   */
  public void add(UUID gatheringId, String memberName) {
    publisher.publishEvent(SharedGatheringMember.builder()
        .gatheringId(gatheringId)
        .memberName(memberName)
        .type("ADMIN")
        .build());
  }

}
