package com.sparta.moim.comment.infrastructure.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
        .defaultCacheConfig()
        //NUll을 캐싱할것인지
        .disableCachingNullValues()
        //기본 캐시 유지 시간 (TTL, Time To Live)
        .entryTtl(Duration.ofSeconds(3600))
        // 캐시를 구분하는 접두사 설정
        .computePrefixWith(CacheKeyPrefix.simple())
        // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
        .serializeValuesWith(
            SerializationPair.fromSerializer(RedisSerializer.java())
        );

    return RedisCacheManager
        .builder(redisConnectionFactory)
        .cacheDefaults(redisCacheConfiguration)
        .build();

  }

}


