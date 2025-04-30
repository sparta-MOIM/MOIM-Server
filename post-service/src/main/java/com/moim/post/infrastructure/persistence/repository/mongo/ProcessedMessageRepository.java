package com.moim.post.infrastructure.persistence.repository.mongo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedMessageRepository extends MongoRepository<ProcessedMessage, String> {
  Optional<ProcessedMessage> findByEventId(UUID eventId);
}
