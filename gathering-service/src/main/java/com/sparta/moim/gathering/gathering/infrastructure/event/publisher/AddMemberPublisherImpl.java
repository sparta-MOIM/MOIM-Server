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

  public void add(UUID gatheringId, String memberName) {
    publisher.publishEvent(SharedGatheringMember.builder()
        .gatheringId(gatheringId)
        .memberName(memberName)
        .type("ADMIN")
        .build());
  }

}
