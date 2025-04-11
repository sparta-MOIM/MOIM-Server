package com.moim.post.application.usecase;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.command.DeleteCommand;
import com.moim.post.application.command.UpdateFeedCommand;
import com.moim.post.application.command.UpdateVoteCommand;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import java.util.UUID;

public interface PostCommandUseCase {
  Feed createFeed(CreateFeedCommand command);
  Feed updateFeed(UUID id, UpdateFeedCommand command);
  Vote createVote(CreateVoteCommand command);
  Vote updateVote(UUID id, UpdateVoteCommand command);
  void deleteFeed(DeleteCommand command);
  void deleteVote(DeleteCommand command);
}
