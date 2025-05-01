package com.sparta.moim.session.session.infrastructure.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaSessionRepository extends JpaRepository<Session, Long>, SessionRepository {
  @Query("""
    select s
    from Session s
    where s.trackingId = :sessionId
      and :now between s.openTime and s.closeTime
""")
  Optional<Session> checkOpenTimeByTrackingId(@Param("sessionId") UUID sessionId, @Param("now") LocalDateTime now);


  @Query("""
     select s from Session s where s.trackingId = :sessionId and s.status <> "OPEN"
     """)
  Optional<Session> checkSessionIdAndStatusOpen(UUID sessionId);
}
