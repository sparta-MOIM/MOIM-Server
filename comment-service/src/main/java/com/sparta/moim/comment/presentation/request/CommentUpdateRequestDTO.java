package com.sparta.moim.comment.presentation.request;


import com.sparta.moim.common.dto.req.RoleCheckDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDTO {
  @NotNull
  private String comment;

  @NotNull
  private RoleCheckDTO roleCheckDTO; //권한 체크를 위한 모임 id (feignClient)
}
