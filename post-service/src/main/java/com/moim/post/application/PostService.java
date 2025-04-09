package com.moim.post.application;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.usecase.PostUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {

  private final FeedRepository feedRepository;

  @Override
  public Feed createFeed(CreateFeedCommand command) {
    Feed feed = Feed.create(
        command.organizationId(),
        command.title(),
        command.context(),
        command.imageUrl(),
        command.taggedIds()
    );
    return feedRepository.save(feed);
  }
}
