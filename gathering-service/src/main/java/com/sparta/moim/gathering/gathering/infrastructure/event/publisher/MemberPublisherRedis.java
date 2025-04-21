package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.dto.event.redis.GatheringJoinAdminEvent;
import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPublisherRedis implements MemberPublisher {
  private final RedisTemplate<String, GatheringJoinAdminEvent> redisTemplate;

  @Override
  public void add(UUID gatheringId, String memberName) {
    GatheringJoinAdminEvent event = new GatheringJoinAdminEvent(gatheringId, memberName, "ADMIN");
    redisTemplate.opsForStream().add("stream:gathering_join", event.toMap());
  }

  // 미구현
  @Override
  public void revoke(UUID id, String owner) {

  }
}
