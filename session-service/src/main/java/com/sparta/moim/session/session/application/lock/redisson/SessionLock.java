package com.sparta.moim.session.session.application.lock.redisson;

import com.sparta.moim.session.session.application.dto.context.SessionRedisExecutionContext;

public interface SessionLock {

  void access(SessionRedisExecutionContext context);
}
