package com.sparta.moim.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sparta.moim.chat.infrastructure.repository")
@EnableMongoRepositories(basePackages = "com.sparta.moim.chat.infrastructure.repository")
public class ChatApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChatApplication.class, args);
  }

}
