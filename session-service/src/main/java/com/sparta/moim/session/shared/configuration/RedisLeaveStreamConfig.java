package com.sparta.moim.session.shared.configuration;


import com.sparta.moim.session.session.infrastructure.event.listener.redis.RedisStreamLeaveListener;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisLeaveStreamConfig {

  private final RedisStreamLeaveListener myStreamListener;

  @Value("${spring.data.redis.stream-leave-key}")
  private String streamKey;

  @Value("${spring.data.redis.group-leave-name}")
  private String groupName;

  @Value("${spring.data.redis.consumer-id}2")
  private String consumerId;

  @Value("${spring.data.redis.poll-timeout}")
  private int pollTimeout;

  @Bean
  public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerLeaveContainer(
      StringRedisTemplate redisTemplate,
      RedisConnectionFactory factory) {


    if (Boolean.FALSE.equals(redisTemplate.hasKey(streamKey))) {
      // 키가 없으면 Dummy 메시지 추가해서 키 만든다
      redisTemplate.boundStreamOps(streamKey).createGroup(ReadOffset.latest(), groupName);
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
