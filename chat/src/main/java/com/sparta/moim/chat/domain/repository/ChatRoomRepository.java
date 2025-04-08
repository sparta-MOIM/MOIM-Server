package com.sparta.moim.chat.domain.repository;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.Optional;

public interface ChatRoomRepository {
  Optional<ChatRoom> save(ChatRoom chatRoom);
}
