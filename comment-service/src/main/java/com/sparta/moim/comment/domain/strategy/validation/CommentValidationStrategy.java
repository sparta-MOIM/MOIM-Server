package com.sparta.moim.comment.domain.strategy.validation;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;

//전략 패턴을 사용하여 댓글, 대댓글 유효성 검증을 구현
//추후 대대댓글과 같은 유효성 검증 로직이 추가되는것을 대비하여 확작성을 고려한 전략패턴을 선택
public interface CommentValidationStrategy {
  void validate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository);
}
