package com.sparta.moim.session.session.infrastructure.repository.redis;

import com.sparta.moim.session.session.domain.repository.redis.SessionSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SessionSeatRedisRepository implements SessionSeatRepository {
  private final RedisTemplate<String, Integer> integerRedisTemplate;

  public void set(String sessionId, Integer remainSeats) {
    integerRedisTemplate.opsForValue().set("session:" + sessionId + ":remain", remainSeats);

  }

}
