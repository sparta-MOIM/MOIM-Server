package com.sparta.moim.comment.infrastructure.repository;

import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
  private final JpaCommentRepository jpaCommentRepository;

  @Override
  public Optional<Comment> save(Comment comment){
    return Optional.of(jpaCommentRepository.save(comment));
  }

  @Override
  public List<Comment> findCommentAll(Long organizationId, Long postId) {return jpaCommentRepository.findByOrganizationIdAndPostIdAndDeletedByIsNullOrderByCreatedDateTimeAsc(organizationId, postId);}

  @Override
  public Comment findComment(Long organizationId, Long postId, Long commentId) {return jpaCommentRepository.findByOrganizationIdAndPostIdAndIdAndDeletedByIsNull(organizationId,postId,commentId);}
}
