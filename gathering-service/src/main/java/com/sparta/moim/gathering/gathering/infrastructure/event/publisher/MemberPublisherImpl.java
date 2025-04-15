package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import com.sparta.moim.gathering.shared.dto.SharedGatheringMember;
import com.sparta.moim.gathering.shared.dto.SharedGatheringRevokeMember;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherImpl implements MemberPublisher {
  private final ApplicationEventPublisher publisher;

  public void add(UUID gatheringId, String memberName) {
    publisher.publishEvent(SharedGatheringMember.builder()
        .gatheringId(gatheringId)
        .memberName(memberName)
        .type("ADMIN")
        .build());
  }

  @Override
  public void revoke(UUID gatheringId, String owner) {
    publisher.publishEvent(SharedGatheringRevokeMember.builder()
        .gatheringId(gatheringId)
        .memberName(owner)
        .build());
  }

}
