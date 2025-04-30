package com.sparta.moim.comment.presentation.request;

import com.sparta.moim.common.dto.req.RoleCheckDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CommentRequestDTO {
  @NotNull
  private String comment;

  @NotNull
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
      message = "게시물 id는 UUID 이어야 합니다.")
  private String postId;

  @NotNull
  @Min(value = 0, message = "commentClass 0(댓글) 혹은 1(대댓글) 중 하나 입니다.")
  @Max(value = 1, message = "commentClass 0(댓글) 혹은 1(대댓글) 중 하나 입니다.")
  private Integer commentClass;

  private String parentId;

  private RoleCheckDTO roleCheckDTO; //권한 체크를 위한 모임 id (feignClient)
}
