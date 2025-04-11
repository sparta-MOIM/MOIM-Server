package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
  Session save(Session session);

  Optional<Session> findByTrackingIdAndDeletedAtIsNull(UUID id);

  boolean existsByTitleAndDeletedByIsNull(String title);

  boolean existsByTitleAndDeletedByIsNullAndTrackingIdNot(String title, UUID sessionId);

}
