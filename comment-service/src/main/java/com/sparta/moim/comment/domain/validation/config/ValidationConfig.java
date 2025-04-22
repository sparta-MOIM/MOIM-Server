package com.sparta.moim.comment.domain.validation.config;

import com.sparta.moim.comment.domain.validation.CommentValidation;
import com.sparta.moim.comment.domain.validation.CommentValidationStrategy;
import com.sparta.moim.comment.domain.validation.ReplyCommentValidation;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
  @Bean
  public Map<Integer, CommentValidationStrategy> validationStrategies(CommentValidation commentValidation, ReplyCommentValidation replyValidation) {
    return Map.of(
        0, commentValidation,
        1, replyValidation
    );
  }
}