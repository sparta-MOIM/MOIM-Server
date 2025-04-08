package com.sparta.moim.comment.infrastructure.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByOrganizationIdAndPostIdAndDeletedByIsNullOrderByCreatedDateTimeAsc(Long organizationId, Long postId);

  Comment findByOrganizationIdAndPostIdAndIdAndDeletedByIsNull(Long organizationId,Long postId,Long id);
}
