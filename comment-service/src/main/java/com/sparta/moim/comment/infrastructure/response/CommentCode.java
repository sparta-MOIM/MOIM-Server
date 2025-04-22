package com.sparta.moim.comment.infrastructure.response;

import com.sparta.moim.common.response.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentCode implements Code {
  COMMENT_FOUND(HttpStatus.OK, "CM200", "댓글을 성공적으로 조회하였습니다."),
  COMMENT_UPDATE(HttpStatus.OK, "CM201", "댓글을 성공적으로 수정하였습니다."),
  COMMENT_DELETE(HttpStatus.OK, "CM202", "댓글을 성공적으로 삭제하였습니다."),
  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CM400", "댓글을 찾을 수 없습니다."),
  PARENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CM401", "부모 댓글을 찾을 수 없습니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND,"CM402", "해당 게시글은 존재하지 않습니다"),
  COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CM403", "댓글 접근권한이 존재하지 않습니다."),
  NO_COMMENT_IN_POST(HttpStatus.FORBIDDEN, "CM404", "해당 게시글에는 댓글이 존재하지 않습니다."),
  COMMENT_NOT_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "CM405", "댓글 삭제에 실패했습니다."),
  COMMENT_NOT_VALID_AUTH(HttpStatus.UNAUTHORIZED, "CM410", "댓글을 작성할 권한이 존재하지 않습니다."),
  PARENT_COMMENT_CANNOT_HAVE_COMMENT(HttpStatus.FORBIDDEN, "CM406", "부모 댓글은 부모 댓글을 가질 수 없습니다.");
  private final HttpStatus status;
  private final String code;
  private final String message;

  CommentCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
