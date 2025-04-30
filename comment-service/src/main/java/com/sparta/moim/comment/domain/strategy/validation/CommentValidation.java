package com.sparta.moim.comment.domain.strategy.validation;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.PARENT_COMMENT_CANNOT_HAVE_COMMENT;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import org.springframework.stereotype.Component;

@Component
public class CommentValidation implements CommentValidationStrategy{
  @Override
  public void validate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository){
    String parentId = commentRequestDTO.getParentId(); //부모 댓글 trackingId 값

    //만약 0(댓글) 이라면 parentId 값은 null 이어야 함. (여기서 전부 유효성 체크)
    if(!parentId.isEmpty()){
      throw new BaseException(PARENT_COMMENT_CANNOT_HAVE_COMMENT);
    }
  }
}
