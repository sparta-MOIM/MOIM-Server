package com.moim.post.infrastructure.persistence.repository;

import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.FeedRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFeedRepository extends JpaRepository<Feed, Long>, FeedRepository {
}
