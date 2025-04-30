package com.moim.post.infrastructure.persistence.repository.mongo;

import com.moim.post.domain.feed.view.FeedView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedViewRepository extends MongoRepository<FeedView, String> {
}
