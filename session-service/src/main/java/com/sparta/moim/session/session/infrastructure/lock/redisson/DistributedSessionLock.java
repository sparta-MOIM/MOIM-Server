package com.sparta.moim.session.session.infrastructure.lock.redisson;

import com.sparta.moim.session.session.application.dto.context.SessionRedisExecutionContext;
import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.lock.redisson.SessionLock;
import com.sparta.moim.session.session.domain.entity.Member;
import com.sparta.moim.session.session.infrastructure.manager.lua.LuaScriptManager;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.DuplicateJoinException;
import com.sparta.moim.session.shared.error.exception.SessionException;
import com.sparta.moim.session.shared.error.exception.SessionFullException;
import com.sparta.moim.session.shared.error.exception.SessionNotInitialized;
import com.sparta.moim.session.shared.error.exception.SessionNotJoinException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSessionLock implements SessionLock {
  private final RedissonClient redissonClient;
  private final RedisTemplate<String, Member> redisTemplate;
  private final LuaScriptManager luaScriptManager;

  @Override
  public void access(SessionRedisExecutionContext context) {
    RLock lock = redissonClient.getLock(context.lockKey());
    SendSessionEventMap event = context.event();
    try {
      // 락 획득 시도 (10초 대기, 30초 유지)
      boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);

      if (!isLocked) {
        throw new SessionException(SessionCode.NOT_FOUND_SESSION);
      }

      try {
        Long execute = redisTemplate.execute(luaScriptManager.load(context.scriptName()),
            List.of(
                "session:" + event.sessionId() + ":remain",
                "session:" + event.sessionId() + ":member",
                context.streamKey()
            ),
            event.memberId(),
            event.sessionId(),
            Instant.now().toString()
        );

        //예외처리
        handleSessionJoinResult(execute.intValue());
      } finally {
        // 락 해제
        lock.unlock();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("락 획득 중 인터럽트 발생", e);
    }
  }

  private void handleSessionJoinResult(Integer result) {
    if (result == null) throw new IllegalStateException("Lua 실행 실패");

    switch (result) {
      case 1 -> log.info("입장/나가기 성공");
      case 0 -> {
        log.warn("좌석이 존재하지 않습니다.");
        throw new SessionFullException();        // 좌석 없음
      }
      case -1 -> {
        log.warn("remain 키가 존재하지 않습니다.");
        throw new SessionNotInitialized();      // remain 키 없음
      }
      case -2 -> {
        log.warn("이미 참여하였습니다.");
        throw new DuplicateJoinException();     // 이미 참가함
      }
      case -3 -> {
        log.warn("입장하지 않았습니다.");
        throw new SessionNotJoinException();     // 입장한 계정이 존재하지 않음
      }
      default -> throw new RuntimeException("예상치 못한 Lua 결과: " + result);
    }
  }
}
