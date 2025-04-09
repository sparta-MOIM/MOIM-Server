package com.sparta.moim.comment.presentation.controller;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.service.CommentDomainService;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.Code;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  private final CommentDomainService commentDomainService;

  @PostMapping("")
  public ResponseEntity<ApiResponseData<String>> postComment(@RequestBody CommentRequestDTO commentRequestDTO){
    commentDomainService.commentService(commentRequestDTO);
    return ResponseEntity.ok().body(ApiResponseData.success(null, "댓글을 성공적으로 등록하였습니다."));
  }

  @GetMapping("/{organization}/{post}")
  public ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> getComments(@PathVariable("organization") Long organizationId,
                                                                               @PathVariable("post") Long postId){
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(), "댓글을 성공적으로 조회하였습니다.", commentDomainService.readAllComment(organizationId,postId)));
  }

  @PutMapping("/{organization}/{post}/{comment_id}")
  public ResponseEntity<ApiResponseData<CommentResponseDTO>> updateComment(@PathVariable("organization") Long organizationId,
                                                                           @PathVariable("post") Long postId,
                                                                           @PathVariable("comment_id") Long commentId,
                                                                           @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO){
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(), "댓글을 성공적으로 수정하였습니다.", commentDomainService.updateComment(organizationId,postId,commentId,commentUpdateRequestDTO)));
  }

  @DeleteMapping("/{organization}/{post}/{comment_id}")
  public ResponseEntity<ApiResponseData<String>> deleteComment(@PathVariable("organization") Long organizationId,
                                                               @PathVariable("post") Long postId,
                                                               @PathVariable("comment_id") Long commentId){
    commentDomainService.deleteComment(organizationId, postId, commentId);
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(),"댓글을 성공적으로 삭제하였습니다.", null));
  }


  @GetMapping("/{post_id}/search")
  public  ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> searchComment(@PathVariable("post_id") Long postId,
                                                                       @RequestParam("comment") String comment){

    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(),"댓글을 성공적으로 조회하였습니다.", commentDomainService.searchComment(postId,comment)));
  }

}
