package com.sparta.moim.session.shared.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
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

  @Bean(name = "integerRedisTemplate")
  public RedisTemplate<String, Integer> integerRedisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Integer> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);

    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));

    return template;
  }
}
