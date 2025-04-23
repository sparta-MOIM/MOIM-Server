package com.sparta.moim.gathering.gathering.infrastructure.configuration.redis;

import com.sparta.moim.gathering.gathering.infrastructure.event.listener.RedisStreamJoinListener;
import io.lettuce.core.RedisBusyException;
import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisJoinStreamConfig {

  private final RedisStreamJoinListener myStreamListener;

  @Value("${spring.data.redis.stream-join-key}")
  private String streamKey;

  @Value("${spring.data.redis.group-join-name}")
  private String groupName;

  @Value("${spring.data.redis.consumer-id}1")
  private String consumerId;

  @Value("${spring.data.redis.poll-timeout}")
  private int pollTimeout;
  @Bean
  public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerJoinContainer(
      StringRedisTemplate redisTemplate,
      RedisConnectionFactory factory) {


    // Group 생성 로직
    StreamOperations<String, Object, Object> streamOps = redisTemplate.opsForStream();

    if (!Boolean.TRUE.equals(redisTemplate.hasKey(streamKey))) {
      streamOps.add(streamKey, Map.of("init", "init"));
    }

    try {
      streamOps.createGroup(streamKey, ReadOffset.latest(), groupName);
    } catch (RedisSystemException e) {
      if (!(e.getCause() instanceof RedisBusyException)) {
        log.error("Redis 그룹 생성 중 오류 발생: {}", e.getMessage(), e);
        throw e;
      }
    }

    var options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
        .builder()
        .pollTimeout(Duration.ofSeconds(pollTimeout)) // Redis polling 간격
        .build();

    var container = StreamMessageListenerContainer.create(factory, options);

    // 여러가지 consumer가 있을 수 있다.
    //서버 3대
    container.receive(
        Consumer.from(groupName, consumerId),
        StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
        myStreamListener
    );
    container.start(); // 꼭 호출해야 함

    return container;
  }

}
