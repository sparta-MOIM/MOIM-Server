package com.moim.post.application.usecase;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;

public interface PostCommandUseCase {
  Feed createFeed(CreateFeedCommand command);
  Vote createVote(CreateVoteCommand command);
}
