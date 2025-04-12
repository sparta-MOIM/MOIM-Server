package com.sparta.moim.session.member.infrastructure.event.publisher;

import com.sparta.moim.session.member.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.shared.dto.SharedDecreaseMember;
import com.sparta.moim.session.shared.dto.SharedIncreaseMember;
import com.sparta.moim.session.shared.dto.SharedRemoveMember;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleSessionMemberCountPublisherImpl implements HandleSessionMemberCountPublisher {
  private final ApplicationEventPublisher publisher;

  public void increase(UUID sessionId, String memberId) {
    publisher.publishEvent(new SharedIncreaseMember(sessionId, memberId));
  }

  public void decrease(UUID sessionId, String memberId) {
    publisher.publishEvent(new SharedDecreaseMember(sessionId, memberId));
  }

  public void remove(UUID sessionId, long count) {
    publisher.publishEvent(new SharedRemoveMember(sessionId, count));
  }

}
