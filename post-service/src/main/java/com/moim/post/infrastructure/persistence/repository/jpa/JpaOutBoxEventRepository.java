package com.moim.post.infrastructure.persistence.repository.jpa;

import com.moim.post.infrastructure.persistence.outbox.OutboxEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOutBoxEventRepository extends JpaRepository<OutboxEvent, UUID> {
  @Query("SELECT e FROM OutboxEvent e WHERE e.isProcessed = false ORDER BY e.createdAt ASC")
  List<OutboxEvent> findUnprocessedEvents();
}
