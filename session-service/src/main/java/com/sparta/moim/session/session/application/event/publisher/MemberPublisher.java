package com.sparta.moim.session.session.application.event.publisher;

import com.sparta.moim.session.session.domain.entity.Session;

public interface MemberPublisher {
  void add(Session session, String memberName);
}
