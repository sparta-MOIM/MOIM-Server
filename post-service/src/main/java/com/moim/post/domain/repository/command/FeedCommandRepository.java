package com.moim.post.domain.repository.command;

import com.moim.post.domain.feed.Feed;
import java.util.Optional;
import java.util.UUID;

public interface FeedCommandRepository {
  Feed save(Feed feed);
  Optional<Feed> findByTrackingId(UUID id);
}
