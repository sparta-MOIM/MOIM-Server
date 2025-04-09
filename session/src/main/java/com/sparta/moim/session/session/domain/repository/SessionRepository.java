package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Session;

public interface SessionRepository {
  Session save(Session session);
}
