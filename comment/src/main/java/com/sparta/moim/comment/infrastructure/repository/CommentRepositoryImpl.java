package com.sparta.moim.comment.infrastructure.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.model.QComment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
  public List<Comment> findCommentAll(Long postId) {return jpaCommentRepository.findByPostIdAndDeletedByIsNullOrderByCreatedAtAsc(postId);}

  @Override
  public Optional<Comment> findComment(Long postId, Long commentId) {
    return Optional.ofNullable(
      jpaCommentRepository.findByPostIdAndIdAndDeletedByIsNull(postId, commentId)
          .orElseThrow(() -> new BaseException(Code.INTERNAL_SERVER_ERROR)));
  }

  @Override
  public List<Comment> searchComment(Long postId, String comment){
    QComment qComment = QComment.comment1;
    return jpaQueryFactory.selectFrom(qComment)
        .where(qComment.postId.eq(postId)
            .and(qComment.comment.containsIgnoreCase(comment)))
        .fetch();
  }

}
