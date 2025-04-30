package com.sparta.moim.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.password}")
  private String password;

  /**
   * RedisConnectionFactory는 스프링 어플리케이션과 레디스를 연결하기 위해 사용된다. 커넥션의 종류로는 Jedis와 Lettuce가 있는데, Lettuce의 성능이 더 좋은 것으로 알려져있다.
   */
  @Bean
  public RedisConnectionFactory redisConnectionFactory() {// redis 연결 정보(host, port, password)를 지정한다.
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        redisHost, redisPort);
    redisStandaloneConfiguration.setPassword(password);

    // redis 연결 정보를 토대로 LettuceConnectionFactory 객체를 생성하여 빈으로 등록한다.
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }
}