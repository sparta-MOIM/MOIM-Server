package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOutboxRepository extends JpaRepository<OutboxEvent, Long>, OutboxRepository {

  @Query("""
    select e from OutboxEvent e where e.status = "PENDING"
    """)
  List<OutboxEvent> findAllByPendingStatusEvents();
}
