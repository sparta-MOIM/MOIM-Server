package com.sparta.moim.comment.presentation.controller;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.domain.service.CommentDomainService;
import com.sparta.moim.comment.infrastructure.response.CommentCode;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.Code;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentDomainService commentDomainService;

  @PostMapping("")
  public ResponseEntity<ApiResponseData<String>> postComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO, @RequestHeader("X-User-ID") String userId){
    commentDomainService.commentService(commentRequestDTO,userId);
    return ResponseEntity.ok().body(ApiResponseData.success(null, "댓글을 성공적으로 등록하였습니다."));
  }

  @GetMapping("/{post_id}")
  public ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> getComments(@PathVariable("post_id") String postId){
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_FOUND.getCode(),  COMMENT_FOUND.getMessage(), commentDomainService.readAllComment(postId)));
  }

  @PutMapping("/{post_id}/{comment_id}")
  public ResponseEntity<ApiResponseData<CommentResponseDTO>> updateComment(@PathVariable("post_id") String postId,
                                                                           @PathVariable("comment_id") UUID commentId,
                                                                           @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO){
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_UPDATE.getCode(), COMMENT_UPDATE.getMessage(), commentDomainService.updateComment(postId,commentId,commentUpdateRequestDTO)));
  }

  @DeleteMapping("/{post}/{comment_id}")
  public ResponseEntity<ApiResponseData<String>> deleteComment(@PathVariable("post") String postId,
                                                               @PathVariable("comment_id") UUID commentId){
    commentDomainService.deleteComment(postId, commentId);
    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_DELETE.getCode(),COMMENT_DELETE.getMessage(), null));
  }


  @GetMapping("/{post_id}/search")
  public  ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> searchComment(@PathVariable("post_id") String postId,
                                                                       @RequestParam("comment") String comment){

    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_FOUND.getCode(), COMMENT_FOUND.getMessage(), commentDomainService.searchComment(postId,comment)));
  }

}
