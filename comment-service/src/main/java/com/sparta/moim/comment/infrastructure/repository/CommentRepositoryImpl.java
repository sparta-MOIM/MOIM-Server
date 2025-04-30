package com.sparta.moim.comment.infrastructure.repository;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.model.QComment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.infrastructure.response.CommentCode;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
  private final JpaCommentRepository jpaCommentRepository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<Comment> save(Comment comment){
    return Optional.of(jpaCommentRepository.save(comment));
  }

  @Override
  public List<Comment> findCommentAll(String postId) {return jpaCommentRepository.findByPostIdAndDeletedByIsNullOrderByCreatedAtAsc(postId);}

  @Override
  public Optional<Comment> findComment(String postId, UUID commentId) {
    return
      jpaCommentRepository.findByPostIdAndTrackingIdAndDeletedByIsNull(postId, commentId);
  }

  @Override
  public List<Comment> searchComment(String postId, String comment){
    QComment qComment = QComment.comment1;
    return jpaQueryFactory.selectFrom(qComment)
        .where(qComment.postId.eq(postId)
            .and(qComment.comment.containsIgnoreCase(comment)))
        .fetch();
  }

  //queryDSL은 반환타입 optional을 허용하지 않는다.
  //만약 값이 없다면 null을 반환
  @Override
  public Comment findUserComment(String postId, UUID commentId, UUID userId){
    QComment qComment = QComment.comment1;
    return jpaQueryFactory.selectFrom(qComment)
        .where(qComment.postId.eq(postId)
            .and(qComment.trackingId.eq(commentId))
            .and(qComment.userId.eq(userId))
            .and(qComment.deletedBy.isNull()))
        .fetchOne();
  }

  @Override
  public void softDeleteByPostId(@Param("postId") String postId, @Param("userId") String userId){
    jpaCommentRepository.softDeleteByPostId(postId,userId);
  }

}
