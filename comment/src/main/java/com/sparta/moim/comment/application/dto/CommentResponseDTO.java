package com.sparta.moim.comment.application.dto;

import com.sparta.moim.comment.domain.model.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {
  private String comment;
  private Long postId;
  private Long organizationId;
  private Integer commentClass;
  private Long parentId;
  private Long userId;

  public static CommentResponseDTO from(Comment comment){
    return CommentResponseDTO.builder()
        .comment(comment.getComment())
        .postId(comment.getPostId())
        .organizationId(comment.getOrganizationId())
        .commentClass(comment.getCommentClass())
        .parentId(comment.getParentId())
        .userId(comment.getUserId())
        .build();
  }

}
