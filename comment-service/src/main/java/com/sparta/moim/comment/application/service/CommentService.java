package com.sparta.moim.comment.application.service;

import static com.sparta.moim.comment.infrastructure.feignclient.enums.OrganizationMemberRole.*;
import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.application.dto.CommentResponseDTO;
import com.sparta.moim.comment.domain.model.Comment;
import com.sparta.moim.comment.domain.repository.CommentRepository;
import com.sparta.moim.comment.domain.validation.CommentValidation;
import com.sparta.moim.comment.domain.validation.CommentValidationStrategy;
import com.sparta.moim.comment.infrastructure.feignclient.PostClient;
import com.sparta.moim.comment.infrastructure.feignclient.RoleCheckClient;
import com.sparta.moim.comment.infrastructure.feignclient.enums.OrganizationMemberRole;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.comment.presentation.request.CommentUpdateRequestDTO;
import com.sparta.moim.common.dto.req.RoleCheckDTO;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostClient postClient;
  private final RoleCheckClient roleCheckClient;
  private final Map<Integer, CommentValidationStrategy> validationStrategies;
  private final UserCheckService userCheckService;

  //댓글 달기
  public void commentService(CommentRequestDTO commentRequestDTO, CustomUserDetails customUserDetails){
    //essential authorization to assign comment
    List<OrganizationMemberRole> roles = List.of(MEMBER, MASTER, MANAGER);
    //권한 체크 후, 아무 일도 없으면 권한 체크 통과
    userCheckService.roleCheck(roleCheckClient, commentRequestDTO.getRoleCheckDTO().getOrganizationId(), customUserDetails.getTrackingId().toString(), roles);

    String postId = commentRequestDTO.getPostId(); //게시물 trackingId;
    Integer commentClass = commentRequestDTO.getCommentClass(); //댓글(0), 대댓글(1) 여부

    //postId가 실제로 존재하는 게시글인지 검사필요. (feignClient)
    //존재하지 않는다면 예외처리
    if(!postClient.isValidFeed(postId).result()){
      throw new BaseException(POST_NOT_FOUND);
    }

    //댓글인지, 대댓글인지 구분해서 유효성 체크 진행 (전략 패턴)
    CommentValidationStrategy validCheck = validationStrategies.get(commentClass);
    validCheck.validate(postId,commentRequestDTO,commentRepository);

    //모든 유효성 체크가 마무리 된 후, 댓글 등록
    Comment comment = Comment.from(commentRequestDTO);
    comment.setUserId(customUserDetails.getTrackingId());
    commentRepository.save(comment);
  }

  //특정 게시물 댓글 전체 조회
  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "commentOfAll", key = "args[0]")
  public List<CommentResponseDTO> readAllComment(RoleCheckDTO roleCheckDTO, String postId, CustomUserDetails customUserDetails){
    //essential authorization to assign comment
    List<OrganizationMemberRole> roles = List.of(MEMBER, MASTER, MANAGER);
    //권한 체크 후, 아무 일도 없으면 권한 체크 통과
    userCheckService.roleCheck(roleCheckClient, roleCheckDTO.getOrganizationId(), customUserDetails.getTrackingId().toString(), roles);

    //이미 등록된 댓글은 게시물이 존재하는지 검사하고 등록된 것이기 때문에
    //조회에서는 굳이 게시물이 존재하는지 검사할 필요없음
    //댓글은 게시물이 삭제되면 같이 삭제되는 구조
    List<Comment> comments = commentRepository.findCommentAll(postId);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();

    for(Comment comment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(comment));
    }
    return commentResponseDTOS;
  }

  //특정 게시글의 특정 댓글 수정
  //댓글 수정 되면 캐시 업데이트
  @Transactional
  @CacheEvict(cacheNames = "commentOfAll", allEntries = true) //댓글이 수정되면 모든 댓글을 조회한 것을 저장한 캐시도 수정 (다른곳에도 추가 필요)
  public CommentResponseDTO updateComment(String postId, UUID commentId, CommentUpdateRequestDTO commentUpdateRequestDTO, CustomUserDetails customUserDetails){
    //essential authorization to assign comment
    List<OrganizationMemberRole> roles = List.of(MEMBER, MASTER, MANAGER);
    //권한 체크 후, 아무 일도 없으면 권한 체크 통과
    userCheckService.roleCheck(roleCheckClient, commentUpdateRequestDTO.getRoleCheckDTO().getOrganizationId(), customUserDetails.getTrackingId().toString(), roles);

    Comment comment = commentRepository.findComment(postId,commentId).orElseThrow(()->new BaseException(COMMENT_NOT_FOUND));
    comment.setComment(commentUpdateRequestDTO.getComment());
    commentRepository.save(comment);

    return CommentResponseDTO.from(comment);
  }

  //특정 게시글의 특정 댓글 삭제
  //게시물이 삭제되면 댓글도 삭제되도록 처리 필요
  @Transactional
  @CacheEvict(cacheNames = "commentOfAll", allEntries = true) //댓글이 수정되면 모든 댓글을 조회한 것을 저장한 캐시도 수정 (다른곳에도 추가 필요)
  public void deleteComment(String postId, UUID commentId, CustomUserDetails customUserDetails){
    Comment comment = commentRepository.findComment(postId,commentId).orElseThrow(()->new BaseException(COMMENT_NOT_FOUND));
    comment.softDelete(customUserDetails.getTrackingId().toString());
    commentRepository.save(comment);
  }

  //특정 게시글의 전체 댓글 삭제
  @Transactional
  @CacheEvict(cacheNames = "commentOfAll", allEntries = true) //댓글이 수정되면 모든 댓글을 조회한 것을 저장한 캐시도 수정 (다른곳에도 추가 필요)
  public boolean deleteComments(String postId, String userId){
    List<Comment> comments = commentRepository.findCommentAll(postId);

    //댓글이 없을 경우, 삭제할 댓글이 없는 경우 이므로, true 반환
    if(comments.isEmpty()){
      return true;
    }

    //댓글 삭제가 성공할 경우, true 반환. 실패하면, false 반환
    try{
      commentRepository.softDeleteByPostId(postId, userId);
      return true;
    }catch(Exception e){
      log.error("댓글 삭제를 실패했습니다. postId: {}, userId: {}", postId, userId, e);
      return false;
    }

  }

  //특정 댓글 내용 바탕으로 댓글 검색
  @Transactional(readOnly = true)
  public List<CommentResponseDTO> searchComment(String postId, String comment){
    List<Comment> comments = commentRepository.searchComment(postId, comment);
    List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
    for(Comment originComment : comments){
      commentResponseDTOS.add(CommentResponseDTO.from(originComment));
    }
    return commentResponseDTOS;
  }

}
