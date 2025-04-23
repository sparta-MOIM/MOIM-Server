package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OutboxRepository {
  OutboxEvent save(OutboxEvent outboxEvent);
  List<OutboxEvent> findAllByPendingStatusEvents(OutboxType outboxType, Pageable pageable);
}
