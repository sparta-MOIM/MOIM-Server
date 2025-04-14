package com.sparta.moim.comment.presentation.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDTO {
  @NotNull
  private String comment;
}
