package com.sparta.moim.gathering.gathering.infrastructure.event.redis;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisProducer {
  private final StringRedisTemplate redis;

  public void sendJoinRequest(String sessionId, String userId) {
    Map<String, String> message = new HashMap<>();
    message.put("session_id", sessionId);
    message.put("user_id", userId);
    redis.opsForStream().add("stream:join_requests", message);
  }
}
