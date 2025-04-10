package com.sparta.moim.comment.presentation.request;

import lombok.Getter;

@Getter
public class CommentRequestDTO {
  private String comment;
  private Long postId;
  private Integer commentClass;
  private Long parentId;
  private Long userId;
}
