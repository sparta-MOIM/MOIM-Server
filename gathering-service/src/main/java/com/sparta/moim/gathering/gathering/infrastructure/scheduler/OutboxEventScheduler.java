package com.sparta.moim.gathering.gathering.infrastructure.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventScheduler {
  private final ObjectMapper objectMapper;
  private final RedisTemplate<String, Member> redisTemplate;
  private final CreateOutboxEvent createOutboxEvent;
  private final OutboxRepository outboxRepository;

  @Scheduled(fixedDelay = 3000)
  public void flushOutboxToRedis() {
    int maxEventsPerBatch = 100;
    Pageable pageable = Pageable.ofSize(maxEventsPerBatch);
    List<OutboxEvent> events = outboxRepository.findAllByPendingStatusEvents(OutboxType.PENDING, pageable);
    for (OutboxEvent event : events) {
      try {
        Map<String, String> map = objectMapper.readValue(event.getPayload(), new TypeReference<>() {
        });
        redisTemplate.opsForStream().add(event.getStreamKey(), map);
        event.markAsSent();
        log.info("Successfully sent event with id {} to Redis stream", event.getId());
      } catch (Exception e) {
        log.error(e.getMessage());
        event.markAsFailed(e.getMessage());
        log.error("Failed to process outbox event with id {}: {}", event.getId(), e.getMessage(), e);
      }
      createOutboxEvent.execute(event);
    }
  }

}
