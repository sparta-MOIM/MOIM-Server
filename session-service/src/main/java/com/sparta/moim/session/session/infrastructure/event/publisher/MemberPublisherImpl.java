package com.sparta.moim.session.session.infrastructure.event.publisher;

import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.enums.MemberType;
import com.sparta.moim.session.session.application.event.publisher.MemberPublisher;
import com.sparta.moim.session.session.domain.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherImpl implements MemberPublisher {
  private final ApplicationEventPublisher publisher;

  public void add(Session session, String memberName) {
    publisher.publishEvent(Member.builder()
        .session(session)
        .memberName(memberName)
        .type(MemberType.PUBLISHER)
        .build());
  }
}
