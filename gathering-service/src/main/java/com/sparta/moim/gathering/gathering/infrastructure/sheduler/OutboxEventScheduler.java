package com.sparta.moim.gathering.gathering.infrastructure.sheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.entity.OutboxEvent;
import com.sparta.moim.gathering.gathering.domain.repository.OutboxRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final OutboxRepository outboxRepository;

  @Scheduled(fixedDelay = 3000)
  @Transactional
  public void flushOutboxToRedis() {
    List<OutboxEvent> events = outboxRepository.findAllByPendingStatusEvents();

    for (OutboxEvent event : events) {
      try {
        Map<String, String> map = objectMapper.readValue(event.getPayload(), new TypeReference<>() {
        });
        redisTemplate.opsForStream().add(event.getStreamKey(), map);
        event.markAsSent();
      } catch (Exception e) {
        log.error(e.getMessage());
        event.markAsFailed();
      }
      outboxRepository.save(event);
    }


  }
}
