package com.moim.post.application;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.usecase.PostCommandUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.command.FeedCommandRepository;
import com.moim.post.domain.repository.command.VoteCommandRepository;
import com.moim.post.domain.vote.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommandService implements PostCommandUseCase {

  private final FeedCommandRepository feedRepository;
  private final VoteCommandRepository voteRepository;

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
}
