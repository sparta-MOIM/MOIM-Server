package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import java.util.List;

public interface OutboxRepository {
  OutboxEvent save(OutboxEvent outboxEvent);

  List<OutboxEvent> findAllByPendingStatusEvents(OutboxType outboxType);
}
