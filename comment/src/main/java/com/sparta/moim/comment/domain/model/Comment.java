package com.sparta.moim.comment.domain.model;

import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@Table(name="comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String comment;

  private Long postId;

  private Long organizationId;

  private Integer commentClass;

  private Long parentId;

  private Long userId;

  public static Comment from(CommentRequestDTO commentRequestDTO){
    return Comment.builder()
        .comment(commentRequestDTO.getComment())
        .postId(commentRequestDTO.getPostId())
        .organizationId(commentRequestDTO.getOrganizationId())
        .commentClass(commentRequestDTO.getCommentClass())
        .parentId(commentRequestDTO.getParentId())
        .userId(commentRequestDTO.getUserId())
        .build();
  }

}
