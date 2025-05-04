package com.sparta.moim.session.session.infrastructure.template.redis;

import com.sparta.moim.session.session.domain.entity.Member;
import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.template.redis.RedisSessionTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLeaveTemplate implements RedisSessionTemplate {
  @Value("${spring.data.redis.stream-leave-key}")
  private String streamLeaveKey;
  private final RedisTemplate<String, Member> redisTemplate;

  public void send(SendSessionEventMap event) {
    redisTemplate.opsForStream().add(streamLeaveKey, event.toMap());
  }

}
