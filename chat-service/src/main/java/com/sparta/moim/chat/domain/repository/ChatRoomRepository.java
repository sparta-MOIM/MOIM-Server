package com.sparta.moim.chat.domain.repository;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
  Optional<ChatRoom> save(ChatRoom chatRoom);
  List<ChatRoom> readChatRooms(String organization_id);

  Optional<ChatRoom> findById(Long chat_room_id);
}
