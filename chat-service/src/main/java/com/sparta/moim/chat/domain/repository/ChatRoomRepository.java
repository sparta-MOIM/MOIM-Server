package com.sparta.moim.chat.domain.repository;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository {
  ChatRoom save(ChatRoom chatRoom);
  List<ChatRoom> readChatRooms(String organization_id);

  ChatRoom findByChatRoomId(String chatRoomId);

  ChatRoom findByTrackingId(UUID trackingId);
}
