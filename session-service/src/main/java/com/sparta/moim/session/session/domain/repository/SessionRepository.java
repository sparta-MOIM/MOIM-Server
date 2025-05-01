package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.query.Param;

public interface SessionRepository {
  Session save(Session session);

  Optional<Session> findByTrackingIdAndDeletedAtIsNull(UUID id);

  boolean existsByTitleAndDeletedByIsNull(String title);

  boolean existsByTitleAndDeletedByIsNullAndTrackingIdNot(String title, UUID sessionId);

  Optional<Session> checkOpenTimeByTrackingId(UUID sessionId, LocalDateTime now);

  Optional<Session> checkSessionIdAndStatusOpen(UUID sessionId);
}
