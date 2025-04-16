package com.sparta.moim.chat.infrastructure.repository.mongo;

import com.sparta.moim.chat.domain.model.Chat;
import com.sparta.moim.chat.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {
  private final MongoChatRepository mongoChatRepository;

  public void save(Chat chat){
    mongoChatRepository.save(chat);
  }
}

