package com.sparta.moim.comment.presentation.controller;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.domain.service.CommentDomainService;
import com.sparta.moim.comment.infrastructure.response.CommentCode;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/comment")
@RequiredArgsConstructor
public class CommentInternalController {

  private final CommentDomainService commentDomainService;
  //실제로 존재하는 게시물의 postId를 보내주셔야 합니다.
  //해당 유저의 trackingId를 보내주셔야 합니다.
  //서비스간의 내부 통신이기 때문에 유저 trackingId가 노출되어도 상관없습니다.
  //댓글 삭제 완료 or 삭제할 댓글이 없는 경우 -> return true
  //삭제 시, 에러가 발생하여 삭제가 안됨 -> return false
  @DeleteMapping("/{postId}/{userId}")
  public boolean deleteComments(@PathVariable("postId") String postId, @PathVariable("userId") String userId){
    return commentDomainService.deleteComments(postId,userId);
  }
}
