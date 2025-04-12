package com.sparta.moim.session.session.infrastructure.event.publisher;

import com.sparta.moim.session.session.application.event.publisher.RemoveMemberPublisher;
import com.sparta.moim.session.shared.dto.SharedRemoveSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveMemberPublisherImpl implements RemoveMemberPublisher {
  private final ApplicationEventPublisher publisher;

  public void remove(SharedRemoveSession removeSession) {
    publisher.publishEvent(removeSession);
  }
}
