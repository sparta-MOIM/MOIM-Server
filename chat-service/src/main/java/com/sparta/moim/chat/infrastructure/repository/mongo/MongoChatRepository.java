package com.sparta.moim.chat.infrastructure.repository.mongo;

import com.sparta.moim.chat.domain.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatRepository extends MongoRepository<Chat, String> {
}
