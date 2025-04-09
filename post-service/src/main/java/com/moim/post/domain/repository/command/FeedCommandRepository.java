package com.moim.post.domain.repository.command;

import com.moim.post.domain.feed.Feed;

public interface FeedCommandRepository {
  Feed save(Feed feed);
}
