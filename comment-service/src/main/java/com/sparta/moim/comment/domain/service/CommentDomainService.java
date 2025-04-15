package com.sparta.moim.comment.domain.service;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.infrastructure.feignclient.PostClient;
import com.sparta.moim.comment.infrastructure.response.CommentCode;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentDomainService {

  private final CommentRepository commentRepository;
  private final PostClient postClient;

  //댓글 달기
  public void commentService(CommentRequestDTO commentRequestDTO, CustomUserDetails customUserDetails){

    //postId가 실제로 존재하는 게시글인지 검사필요. (feignClient)
    //존재하지 않는다면 예외처리
    if(!postClient.isValidFeed(commentRequestDTO.getPostId()).result()){
      throw new BaseException(POST_NOT_FOUND);
    }

    Comment comment = Comment.from(commentRequestDTO);
    comment.setUserId(customUserDetails.getTrackingId());
    commentRepository.save(comment);
  }

  //특정 게시물 댓글 전체 조회
  public List<CommentResponseDTO> readAllComment(String postId){

    //이미 등록된 댓글은 게시물이 존재하는지 검사하고 등록된 것이기 때문에
    //조회에서는 굳이 게시물이 존재하는지 검사할 필요없음
    //댓글은 게시물이 삭제되면 같이 삭제되는 구조
    List<Comment> comments = commentRepository.findCommentAll(postId);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();

    for(Comment comment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(comment));
    }
    return commentResponseDTOS;
  }

  //특정 게시글의 특정 댓글 수정
  public CommentResponseDTO updateComment(String postId, UUID commentId, CommentUpdateRequestDTO commentUpdateRequestDTO){
    Comment comment = commentRepository.findComment(postId,commentId).orElseThrow(()->new BaseException(COMMENT_NOT_FOUND));
    comment.setComment(commentUpdateRequestDTO.getComment());
    commentRepository.save(comment);

    return CommentResponseDTO.from(comment);
  }

  //특정 게시글의 특정 댓글 삭제
  //게시물이 삭제되면 댓글도 삭제되도록 처리 필요
  public void deleteComment(String postId, UUID commentId){
    Comment comment = commentRepository.findComment(postId,commentId).get();
    comment.softDelete("testUsername");
    commentRepository.save(comment);

  }

  //특정 댓글 내용 바탕으로 댓글 검색
  public List<CommentResponseDTO> searchComment(String postId, String comment){
    List<Comment> comments = commentRepository.searchComment(postId, comment);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
    for(Comment originComment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(originComment));
    }
    return commentResponseDTOS;
  }

}
