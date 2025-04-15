package com.sparta.moim.comment.application.dto;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {
  private Long id;
  private String comment;
  private String postId;
  private Integer commentClass;
  private Long parentId;
  private UUID userId;

  public static CommentResponseDTO from(Comment comment){
    return CommentResponseDTO.builder()
        .id(comment.getId())
        .comment(comment.getComment())
        .postId(comment.getPostId())
        .commentClass(comment.getCommentClass())
        .parentId(comment.getParentId())
        .userId(comment.getUserId())
        .build();
  }

}
