package com.sparta.moim.session.session.infrastructure.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaSessionRepository extends JpaRepository<Session, Long>, SessionRepository {
  @Query("""
    select s
    from Session s
    where s.trackingId = :sessionId
      and CURRENT_TIMESTAMP between s.openTime and s.closeTime
""")
  Optional<Session> checkOpenTimeByTrackingId(UUID sessionId);


  @Query("""
     select s from Session s where s.trackingId = :sessionId and s.status <> "OPEN"
     """)
  Optional<Session> checkSessionIdAndStatusOpen(UUID sessionId);
}
