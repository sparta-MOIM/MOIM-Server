package com.sparta.moim.comment.infrastructure.feignclient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@EnableFeignClients("post-service")
public interface PostClient {
  @GetMapping("/internal/v1/posts/feeds/{id}")
  ValidationResponse isValidFeed(@PathVariable final String id);
}
