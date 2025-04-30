package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAddAdminEvent;
import com.sparta.moim.gathering.gathering.application.dto.event.GatheringRevokeAdminEvent;
import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherEventStream implements MemberPublisher {
  private final ApplicationEventPublisher publisher;

  @Override
  public void add(UUID gatheringId, UUID memberName) {
    publisher.publishEvent(GatheringAddAdminEvent.builder()
        .gatheringId(gatheringId)
        .memberName(memberName)
        .type("ADMIN")
        .build());
  }

  @Override
  public void revoke(UUID gatheringId, UUID owner) {
    publisher.publishEvent(GatheringRevokeAdminEvent.builder()
        .gatheringId(gatheringId)
        .ownerId(owner)
        .build());
  }

}
