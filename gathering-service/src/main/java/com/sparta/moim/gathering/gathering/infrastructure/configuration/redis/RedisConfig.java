package com.sparta.moim.gathering.gathering.infrastructure.configuration.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, T> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);

    StringRedisSerializer serializer = new StringRedisSerializer();

    template.setKeySerializer(serializer);
    template.setValueSerializer(serializer);

    template.setHashKeySerializer(serializer);
    template.setHashValueSerializer(serializer);

    return template;
  }

}
