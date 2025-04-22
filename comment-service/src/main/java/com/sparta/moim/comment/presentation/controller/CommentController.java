package com.sparta.moim.comment.presentation.controller;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.application.service.CommentService;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.dto.req.RoleCheckDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("")
  public ResponseEntity<ApiResponseData<String>> postComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails){
    commentService.createComment(commentRequestDTO,customUserDetails);
    return ResponseEntity.ok().body(ApiResponseData.success(null, "댓글을 성공적으로 등록하였습니다."));
  }

  @GetMapping("/{organizationId}/{postId}")
  public ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> getComments(@PathVariable("organizationId") String organizationId,
                                                                               @PathVariable("postId") String postId,
                                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_FOUND.getCode(),  COMMENT_FOUND.getMessage(), commentService.readAllComment(organizationId, postId, customUserDetails)));
  }

  @PutMapping("/{post_id}/{comment_id}")
  public ResponseEntity<ApiResponseData<CommentResponseDTO>> updateComment(@PathVariable("post_id") String postId,
                                                                           @PathVariable("comment_id") UUID commentId,
                                                                           @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO,
                                                                           @AuthenticationPrincipal CustomUserDetails customUserDetails){
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_UPDATE.getCode(), COMMENT_UPDATE.getMessage(), commentService.updateComment(postId,commentId,commentUpdateRequestDTO, customUserDetails)));
  }

  @DeleteMapping("/{post}/{comment_id}")
  public ResponseEntity<ApiResponseData<String>> deleteComment(@RequestBody @Valid RoleCheckDTO roleCheckDTO,
                                                               @PathVariable("post") String postId,
                                                               @PathVariable("comment_id") UUID commentId,
                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
    commentService.deleteComment(roleCheckDTO, postId, commentId, customUserDetails);
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_DELETE.getCode(),COMMENT_DELETE.getMessage(), null));
  }


  @GetMapping("/{organizationId}/{postId}/search")
  public  ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> searchComment(@PathVariable("organizationId") String organizationId,
                                                                                  @PathVariable("postId") String postId,
                                                                                  @RequestParam("comment") String comment,
                                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails){

    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_FOUND.getCode(), COMMENT_FOUND.getMessage(), commentService.searchComment(organizationId, postId,comment,customUserDetails)));
  }

}
