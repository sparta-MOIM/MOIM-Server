package com.moim.post.application.usecase;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.domain.feed.Feed;

public interface PostUseCase {
  Feed createFeed(CreateFeedCommand command);
}
