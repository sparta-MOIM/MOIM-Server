package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaOutboxRepository extends JpaRepository<OutboxEvent, Long>, OutboxRepository {

  @Query("""
    select e from OutboxEvent e where e.status = :status
    """)
  List<OutboxEvent> findAllByPendingStatusEvents(@Param("status") OutboxType status);
}
