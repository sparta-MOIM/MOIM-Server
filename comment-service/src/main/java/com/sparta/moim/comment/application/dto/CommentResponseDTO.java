package com.sparta.moim.comment.application.dto;

import com.sparta.moim.comment.domain.model.Comment;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO implements Serializable {
  private Long id;
  private String comment;
  private String postId;
  private Integer commentClass;
  private String parentId;
  private UUID userId;
  private UUID trackingId;

  public static CommentResponseDTO from(Comment comment){
    return CommentResponseDTO.builder()
        .id(comment.getId())
        .comment(comment.getComment())
        .postId(comment.getPostId())
        .commentClass(comment.getCommentClass())
        .parentId(comment.getParentId())
        .userId(comment.getUserId())
        .trackingId(comment.getTrackingId())
        .build();
  }

}
