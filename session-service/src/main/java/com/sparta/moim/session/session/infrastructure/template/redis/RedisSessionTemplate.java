package com.sparta.moim.session.session.infrastructure.template.redis;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.template.redis.SessionTemplate;
import com.sparta.moim.session.session.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSessionTemplate implements SessionTemplate {
  @Value("${spring.data.redis.stream-join-key}")
  private String streamJoinKey;
  @Value("${spring.data.redis.stream-leave-key}")
  private String streamLeaveKey;

  private final RedisTemplate<String, Member> redisTemplate;

  public void join(SendSessionEventMap event) {
    redisTemplate.opsForStream().add(streamJoinKey, event.toMap());
  }

  public void leave(SendSessionEventMap event) {
    redisTemplate.opsForStream().add(streamLeaveKey, event.toMap());
  }
}
