package com.sparta.moim.comment.domain.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  Optional<Comment> save(Comment comment);

  List<Comment> findCommentAll(Long organizationId, Long postId);

  Comment findComment(Long organizationId,Long postId,Long commentId);
}

