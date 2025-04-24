package com.sparta.moim.gathering.gathering.infrastructure.scheduler;

import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateOutboxEvent {
  private final OutboxRepository outboxRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void execute(OutboxEvent event) {
    outboxRepository.save(event);
  }
}
