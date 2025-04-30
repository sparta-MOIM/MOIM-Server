package com.moim.post.infrastructure.persistence.repository.jpa;

import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.command.FeedCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFeedRepository extends JpaRepository<Feed, Long>, FeedCommandRepository {
}
