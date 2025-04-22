package com.sparta.moim.comment.domain.validation;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;

public interface CommentValidationStrategy {
  void validate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository);
}
