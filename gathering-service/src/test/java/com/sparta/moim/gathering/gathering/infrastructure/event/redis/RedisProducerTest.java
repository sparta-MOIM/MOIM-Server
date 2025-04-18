package com.sparta.moim.gathering.gathering.infrastructure.event.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = {
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379"
})
class RedisProducerTest {

  @Autowired
  private RedisProducer redisProducer;

  @Test
  void testSendJoinRequest() {
    redisProducer.sendJoinRequest("session-2", "user-124");
    redisProducer.sendJoinRequest("session-2", "user-454");
  }
}
