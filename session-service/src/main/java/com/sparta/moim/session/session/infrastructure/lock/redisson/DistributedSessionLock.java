package com.sparta.moim.session.session.infrastructure.lock.redisson;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.lock.redisson.SessionLock;
import com.sparta.moim.session.session.domain.entity.Member;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributedSessionLock implements SessionLock {
  private final RedissonClient redissonClient;
  private final RedisTemplate<String, Member> redisTemplate;

  @Override
  public void access(SendSessionEventMap event, String lockKey, String key) {
    RLock lock = redissonClient.getLock(lockKey);

    try {
      // 락 획득 시도 (10초 대기, 30초 유지)
      boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);

      if (!isLocked) {
        throw new SessionException(SessionCode.NOT_FOUND_SESSION);
      }

      try {
        redisTemplate.opsForStream().add(key, event.toMap());
      } finally {
        // 락 해제
        lock.unlock();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("락 획득 중 인터럽트 발생", e);
    }
  }
}
