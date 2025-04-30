package com.sparta.moim.comment.domain.comment;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.domain.strategy.validation.CommentValidation;
import com.sparta.moim.comment.domain.strategy.validation.ReplyCommentValidation;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//전략 패턴 사용
//전략 컨텍스트 (전략 등록/실행)
@Component
@RequiredArgsConstructor
public class CommentValidationContext {

  private final CommentValidation commentValidation;
  private final ReplyCommentValidation replyCommentValidation;

  public void commentValidate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository){
    commentValidation.validate(postId, commentRequestDTO, commentRepository);
  }

  public void replyCommentValidate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository){
    replyCommentValidation.validate(postId, commentRequestDTO, commentRepository);
  }
}
