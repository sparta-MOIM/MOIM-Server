package com.moim.post.application.usecase;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.command.DeleteCommand;
import com.moim.post.application.command.UpdateFeedCommand;
import com.moim.post.application.command.UpdateVoteCommand;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.UUID;

public interface PostCommandUseCase {
  Feed createFeed(CustomUserDetails userDetails, CreateFeedCommand command);
  Feed updateFeed(CustomUserDetails userDetails, UUID id, UpdateFeedCommand command);
  Vote createVote(CustomUserDetails userDetails, CreateVoteCommand command);
  Vote updateVote(CustomUserDetails userDetails, UUID id, UpdateVoteCommand command);
  void deleteFeed(CustomUserDetails userDetails, DeleteCommand command);
  void deleteVote(CustomUserDetails userDetails, DeleteCommand command);
}
