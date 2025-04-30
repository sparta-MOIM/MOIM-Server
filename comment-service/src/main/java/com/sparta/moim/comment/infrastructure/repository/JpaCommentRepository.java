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

  //Modifying 어노테이션은 executeUpdate() - 벌크업데이트 를 자동으로 수행해준다.
  //벌크업데이트는 DB에 바로 쿼리를 날려주는 것이라 영속성 컨텍스트와 일치하지 않는 문제가 발생할 수 있다. (실제 디비랑 영속성 컨텍스트 간의 데이터 불일치 문제가 발생할 수 있다)
  //그래서 벌크업데이트 후에 flush 나 clear를 해줘야 한다.
  //clearAutomatically = true - clear()
  //flushAutomatically = true - flush()
  @Modifying(clearAutomatically = true)
  @Query("UPDATE Comment c set c.deletedBy = :userId, c.deletedAt = CURRENT_TIMESTAMP WHERE c.postId = :postId")
  void softDeleteByPostId(@Param("postId") String postId, @Param("userId") String userId);
}
