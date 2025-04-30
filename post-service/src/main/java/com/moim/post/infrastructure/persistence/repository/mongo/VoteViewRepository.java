package com.moim.post.infrastructure.persistence.repository.mongo;

import com.moim.post.domain.vote.view.VoteView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteViewRepository extends MongoRepository<VoteView, String> {
}
