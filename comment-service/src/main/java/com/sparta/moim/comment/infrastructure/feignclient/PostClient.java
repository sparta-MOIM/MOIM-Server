package com.sparta.moim.comment.infrastructure.feignclient;

import com.sparta.moim.comment.infrastructure.feignclient.dto.ValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service")
public interface PostClient {
  @GetMapping("/internal/v1/posts/feeds/{id}")
  ValidationResponse isValidFeed(@PathVariable("id") final String id);
}
