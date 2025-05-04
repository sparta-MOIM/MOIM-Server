package com.sparta.moim.session.session.infrastructure.template.redis;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.template.redis.RedisSessionTemplate;
import com.sparta.moim.session.session.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisJoinTemplate implements RedisSessionTemplate {
  @Value("${spring.data.redis.stream-join-key}")
  private String streamJoinKey;

  private final RedisTemplate<String, Member> redisTemplate;

  public void send(SendSessionEventMap event) {
    redisTemplate.opsForStream().add(streamJoinKey, event.toMap());
  }
}
