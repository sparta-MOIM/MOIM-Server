package com.sparta.moim.session.session.application.lock.redisson;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;

public interface SessionLock {

  void access(SendSessionEventMap event, String lockKey, String streamKey);
}
