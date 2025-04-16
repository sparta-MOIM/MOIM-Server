package com.moim.post.application;

import com.moim.post.application.exception.NotFoundFeed;
import com.moim.post.application.exception.NotFoundVote;
import com.moim.post.application.exception.PostUnauthorizedAccessException;
import com.moim.post.application.query.FindQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.application.query.SearchVoteQuery;
import com.moim.post.application.usecase.PostQueryUseCase;
import com.moim.post.application.validation.RoleValidator;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.query.FeedQueryRepository;
import com.moim.post.domain.repository.query.VoteQueryRepository;
import com.moim.post.domain.vote.Vote;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService implements PostQueryUseCase {

  private final FeedQueryRepository feedRepository;
  private final VoteQueryRepository voteRepository;
  private final RoleValidator roleValidator;

  @Override
  public Feed findFeed(
      CustomUserDetails userDetails,
      FindQuery query
  ) {
    checkRole("READ", query.id(), userDetails.getTrackingId());
    return feedRepository.findFeed(query.id()).orElseThrow(NotFoundFeed::new);
  }

  @Override
  public Page<Feed> searchFeed(
      CustomUserDetails userDetails,
      SearchFeedQuery query,
      int page,
      int size,
      String sortType
  ) {
    checkRole("READ", query.organizationId().get(), userDetails.getTrackingId());
    Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, sortType));
    return feedRepository.searchFeed(
        query.organizationId(),
        query.word(),
        pageable
    );
  }

  @Override
  public Vote findVote(
      CustomUserDetails userDetails,
      FindQuery query
  ) {
    checkRole("READ", query.id(), userDetails.getTrackingId());
    return voteRepository.findVote(query.id()).orElseThrow(NotFoundVote::new);
  }

  @Override
  public Page<Vote> searchVote(
      CustomUserDetails userDetails,
      SearchVoteQuery query,
      int page,
      int size,
      String sortType
  ) {
    checkRole("READ", query.organizationId().get(), userDetails.getTrackingId());
    Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, sortType));
    return voteRepository.searchVote(
        query.organizationId(),
        query.word(),
        query.start(),
        query.end(),
        pageable
    );
  }

  @Override
  public Boolean isValidFeed(FindQuery query) {
    return feedRepository.findFeed(query.id())
        .filter(feed -> !feed.getIsDeleted())
        .isPresent();
  }

  private void checkRole(
      String requestType,
      UUID organizationTrackingId,
      UUID userTrackingId
  ) {
    Boolean result = roleValidator.isValidRole(requestType, organizationTrackingId, userTrackingId);
    if (!result) {
      throw new PostUnauthorizedAccessException();
    }
  }

}
