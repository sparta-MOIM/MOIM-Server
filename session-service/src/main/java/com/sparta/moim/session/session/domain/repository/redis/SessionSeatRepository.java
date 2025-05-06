package com.sparta.moim.session.session.domain.repository.redis;

public interface SessionSeatRepository {
  void set(String sessionId, Integer remainSeats);
}
