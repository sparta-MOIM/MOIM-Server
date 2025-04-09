package com.sparta.moim.comment.domain.model;

import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

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
  @Column(columnDefinition = "TEXT", nullable = false)
  private String comment;

  @Column(nullable = false)
  private Long postId;

  private Long organizationId;

  @Column(nullable = false)
  private Integer commentClass;

  private Long parentId;

  @Column(nullable = false)
  private Long userId;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false, unique = true)
  private UUID trackingId;

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
