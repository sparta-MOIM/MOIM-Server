package com.sparta.moim.comment.infrastructure.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostIdAndDeletedByIsNullOrderByCreatedAtAsc(String postId);

  Optional<Comment> findByPostIdAndTrackingIdAndDeletedByIsNull(String postId, UUID trackingId);

  @Modifying
  @Query("UPDATE Comment c set c.deletedBy = :userId, c.deletedAt = CURRENT_TIMESTAMP WHERE c.postId = :postId")
  void softDeleteByPostId(@Param("postId") String postId, @Param("userId") String userId);
}
