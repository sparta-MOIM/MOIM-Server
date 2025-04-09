package com.sparta.moim.comment.domain.service;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDomainService {

  private final CommentRepository commentRepository;

  //댓글 달기
  public void commentService(CommentRequestDTO commentRequestDTO){
    Comment comment = Comment.from(commentRequestDTO);
    commentRepository.save(comment);
  }

  //특정 모임의 특정 게시물 댓글 전체 조회
  public List<CommentResponseDTO> readAllComment(Long organizationId, Long postId){
    List<Comment> comments = commentRepository.findCommentAll(organizationId,postId);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();

    for(Comment comment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(comment));
    }
    return commentResponseDTOS;
  }

  //특정 모임, 특정 게시글, 특정 댓글 수정
  public CommentResponseDTO updateComment(Long organizationId, Long postId, Long commentId, CommentUpdateRequestDTO commentUpdateRequestDTO){
    Comment comment = commentRepository.findComment(organizationId,postId,commentId).orElseThrow(()->new BaseException("해당 댓글을 찾을 수 없습니다."));
    comment.setComment(commentUpdateRequestDTO.getComment());
    commentRepository.save(comment);

    return CommentResponseDTO.from(comment);
  }

  //특정 모임, 특정 게시글, 특정 댓글 삭제
  public void deleteComment(Long organizationId, Long postId, Long commentId){
    Comment comment = commentRepository.findComment(organizationId,postId,commentId).get();
    comment.softDelete("testUsername");
    commentRepository.save(comment);

  }

  //특정 댓글 내용 바탕으로 댓글 검색
  public List<CommentResponseDTO> searchComment(Long postId, String comment){
    List<Comment> comments = commentRepository.searchComment(postId, comment);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
    for(Comment originComment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(originComment));
    }
    return commentResponseDTOS;
  }

}
