package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import java.util.List;

public interface OutboxRepository {
  OutboxEvent save(OutboxEvent OutboxEvent);
  List<OutboxEvent> findAllByPendingStatusEvents();
}
