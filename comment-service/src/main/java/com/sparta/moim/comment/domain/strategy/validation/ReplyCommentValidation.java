package com.sparta.moim.comment.domain.strategy.validation;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.PARENT_COMMENT_NOT_FOUND;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReplyCommentValidation implements CommentValidationStrategy{
  @Override
  public void validate(String postId, CommentRequestDTO commentRequestDTO, CommentRepository commentRepository){
    String parentId = commentRequestDTO.getParentId(); //부모 댓글 trackingId 값

    //commentClass 가 1(대댓글) 이라면, parentId가 null 이 아닌지 검사,
    if(parentId.isEmpty()){
      throw new BaseException(PARENT_COMMENT_NOT_FOUND);
    }

    //commentClass 가 1(대댓글) 이고, parentId가 null이 아니라면, 실제로 해당 댓글(parentId)이 존재 하는지 체크
    commentRepository.findComment(postId, UUID.fromString(parentId)).orElseThrow(()->new BaseException(PARENT_COMMENT_NOT_FOUND));
  }
}
