package com.sparta.moim.chat.infrastructure.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//MongoConfig 클래스를 작성하지 않고 MongoRepository를 상속받아서 사용하는 것도 가능하지만,
//동적 쿼리 문제를 해결하려면 MongoTemplate의 도움을 받아야 하기 때문에 저는 설정 클래스를 따로 만들어 주었습니다.
@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "com.sparta.moim.chat")
public class MongoConfig {

  private final MongoProperties mongoProperties;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(mongoProperties.getClient());
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient(), mongoProperties.getName());
  }

}
