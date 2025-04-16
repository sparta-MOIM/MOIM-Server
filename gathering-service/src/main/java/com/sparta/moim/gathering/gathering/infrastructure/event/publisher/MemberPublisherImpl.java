package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAdminSaveEvent;
import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAdminRevokeEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherImpl implements MemberPublisher {
  private final ApplicationEventPublisher publisher;

  @Override
  public void add(UUID gatheringId, String memberName) {
    publisher.publishEvent(GatheringAdminSaveEvent.builder()
        .gatheringId(gatheringId)
        .memberName(memberName)
        .type("ADMIN")
        .build());
  }

  @Override
  public void revoke(UUID gatheringId, String owner) {
    publisher.publishEvent(GatheringAdminRevokeEvent.builder()
        .gatheringId(gatheringId)
        .ownerName(owner)
        .build());
  }

}
