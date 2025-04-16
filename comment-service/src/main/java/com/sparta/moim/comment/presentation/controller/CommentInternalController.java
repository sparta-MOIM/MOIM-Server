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
  @DeleteMapping("/{postId}")
  public ResponseEntity<ApiResponseData<String>> deleteComments(@PathVariable("postId") String postId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
    commentDomainService.deleteComments(postId,customUserDetails);
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_DELETE.getCode(), COMMENT_DELETE.getMessage(),null ));
  }
}
