package com.sparta.moim.session.session.infrastructure.event.publisher;

import com.sparta.moim.session.session.application.event.publisher.MemberPublisher;
import com.sparta.moim.session.shared.dto.SharedSessionMember;
import com.sparta.moim.session.shared.enums.MemberType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherImpl implements MemberPublisher {
  private final ApplicationEventPublisher publisher;

  public void add(UUID sessionId, String memberName) {
    publisher.publishEvent(SharedSessionMember.builder()
        .sessionId(sessionId)
        .memberName(memberName)
        .type(MemberType.PUBLISHER.name())
        .build());
  }
}
