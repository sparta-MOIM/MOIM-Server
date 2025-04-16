package com.moim.post.application;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.command.DeleteCommand;
import com.moim.post.application.command.UpdateFeedCommand;
import com.moim.post.application.command.UpdateVoteCommand;
import com.moim.post.application.exception.NotFoundFeed;
import com.moim.post.application.exception.NotFoundVote;
import com.moim.post.application.exception.PostUnauthorizedAccessException;
import com.moim.post.application.usecase.PostCommandUseCase;
import com.moim.post.application.validation.RoleValidator;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.command.FeedCommandRepository;
import com.moim.post.domain.repository.command.VoteCommandRepository;
import com.moim.post.domain.repository.command.entitymanager.FeedEntityManager;
import com.moim.post.domain.vote.Vote;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService implements PostCommandUseCase {

  private final FeedCommandRepository feedRepository;
  private final VoteCommandRepository voteRepository;
  private final FeedEntityManager feedEntityManager;
  private final RoleValidator roleValidator;

  @Override
  public Feed createFeed(
      CustomUserDetails userDetails,
      CreateFeedCommand command
  ) {
    checkRole("CREATE", command.organizationId(), userDetails.getTrackingId());
    Feed feed = Feed.create(
        command.organizationId(),
        command.title(),
        command.content(),
        command.imageUrl(),
        command.taggedIds()
    );
    return feedRepository.save(feed);
  }

  @Override
  public Vote createVote(
      CustomUserDetails userDetails,
      CreateVoteCommand command
  ) {
    checkRole("CREATE", command.organizationId(), userDetails.getTrackingId());
    Vote vote = Vote.create(
        command.organizationId(),
        command.title(),
        command.content(),
        command.start(),
        command.end(),
        command.totalVoter()
    );
    return voteRepository.save(vote);
  }

  @Override
  public Feed updateFeed(
      CustomUserDetails userDetails,
      UUID id,
      UpdateFeedCommand command
  ) {
    checkRole("UPDATE", command.organizationId(), userDetails.getTrackingId());
    Feed feed = feedRepository.findByTrackingId(id).orElseThrow(NotFoundFeed::new);
    command.title().ifPresent(feed::updateTitle);
    command.content().ifPresent(feed::updateContent);
    command.imageUrl().ifPresent(feed::updateImageUrl);
    command.taggedUserIds().ifPresent(
        userIds ->
            feedEntityManager.updateTaggedUserIds(feed.getId(), userIds)
    );
    return feed;
  }

  @Override
  public Vote updateVote(
      CustomUserDetails userDetails,
      UUID id,
      UpdateVoteCommand command
  ) {
    checkRole("UPDATE", command.organizationId(), userDetails.getTrackingId());
    Vote vote = voteRepository.findByTrackingId(id).orElseThrow(NotFoundVote::new);
    command.title().ifPresent(vote::updateTitle);
    command.content().ifPresent(vote::updateContent);
    command.start().ifPresent(vote::updateStart);
    command.end().ifPresent(vote::updateEnd);
    command.totalVoter().ifPresent(vote::updateTotalVoter);
    return vote;
  }

  @Override
  public void deleteFeed(
      CustomUserDetails userDetails,
      DeleteCommand command
  ) {
    checkRole("DELETE", command.organizationId(), userDetails.getTrackingId());
    Feed feed = feedRepository.findByTrackingId(command.postId()).orElseThrow(NotFoundFeed::new);
    feed.delete();
    // todo: 댓글 도메인에 삭제 이벤트 발행
  }

  @Override
  public void deleteVote(
      CustomUserDetails userDetails,
      DeleteCommand command
  ) {
    checkRole("DELETE", command.organizationId(), userDetails.getTrackingId());
    Vote vote = voteRepository.findByTrackingId(command.postId()).orElseThrow(NotFoundVote::new);
    vote.delete();
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

