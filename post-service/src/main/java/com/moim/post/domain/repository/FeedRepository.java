package com.moim.post.domain.repository;

import com.moim.post.domain.feed.Feed;

public interface FeedRepository {
  Feed save(Feed feed);
}
