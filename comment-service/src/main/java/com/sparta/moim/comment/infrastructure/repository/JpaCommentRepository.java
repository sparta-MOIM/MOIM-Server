package com.sparta.moim.comment.infrastructure.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostIdAndDeletedByIsNullOrderByCreatedAtAsc(String postId);

  Optional<Comment> findByPostIdAndTrackingIdAndDeletedByIsNull(String postId, UUID trackingId);
}
