package com.sparta.moim.chat.infrastructure.repository;

import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
  private final JpaChatRoomRepository jpaChatRoomRepository;

  @Override
  public Optional<ChatRoom> save(ChatRoom chatRoom) {
    return Optional.of(jpaChatRoomRepository.save(chatRoom));
  }
}
