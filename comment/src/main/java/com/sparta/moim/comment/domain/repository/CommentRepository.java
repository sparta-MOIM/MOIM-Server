package com.sparta.moim.comment.domain.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  Optional<Comment> save(Comment comment);

  List<Comment> findCommentAll(Long postId);

  Optional<Comment> findComment(Long postId,Long commentId);

  List<Comment> searchComment(Long postId, String comment);
}

