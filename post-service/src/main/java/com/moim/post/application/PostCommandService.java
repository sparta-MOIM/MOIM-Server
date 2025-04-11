package com.moim.post.application;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.command.UpdateFeedCommand;
import com.moim.post.application.command.UpdateVoteCommand;
import com.moim.post.application.usecase.PostCommandUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.command.FeedCommandRepository;
import com.moim.post.domain.repository.command.VoteCommandRepository;
import com.moim.post.domain.repository.command.entitymanager.FeedEntityManager;
import com.moim.post.domain.vote.Vote;
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

  @Override
  public Feed createFeed(CreateFeedCommand command) {
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
  public Vote createVote(CreateVoteCommand command) {
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
  public Feed updateFeed(UUID id, UpdateFeedCommand command) {
    // todo : 예외처리
    Feed feed = feedRepository.findByTrackingId(id).orElseThrow(null);
    command.title().ifPresent(feed::updateTitle);
    command.content().ifPresent(feed::updateContent);
    command.imageUrl().ifPresent(feed::updateImageUrl);
//    command.taggedUserIds().ifPresent(feed::updateTaggedUserIds);
    command.taggedUserIds().ifPresent(
        userIds ->
            feedEntityManager.updateTaggedUserIds(feed.getId(), userIds)
    );
    return feed;
  }

  @Override
  public Vote updateVote(UUID id, UpdateVoteCommand command) {
    // todo : 예외처리
    Vote vote = voteRepository.findByTrackingId(id).orElseThrow(null);
    command.title().ifPresent(vote::updateTitle);
    command.content().ifPresent(vote::updateContent);
    command.start().ifPresent(vote::updateStart);
    command.end().ifPresent(vote::updateEnd);
    command.totalVoter().ifPresent(vote::updateTotalVoter);
    return vote;
  }
}

