package com.moim.post.domain.repository.query;

import com.moim.post.domain.feed.Feed;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedQueryRepository {
  Optional<Feed> findFeed(UUID id);

  Page<Feed> searchFeed(Optional<UUID> organizationId, Optional<String> word, Pageable pageable);
}
