package com.sparta.moim.comment.domain.repository;

import com.sparta.moim.comment.domain.model.Comment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
  Optional<Comment> save(Comment comment);

  List<Comment> findCommentAll(String postId);

  Optional<Comment> findComment(String postId, UUID commentId);

  List<Comment> searchComment(String postId, String comment);
}

