package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
  Session save(Session session);
  Optional<Session> findByTrackingId(UUID id);
}
