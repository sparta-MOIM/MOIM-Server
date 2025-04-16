package com.sparta.moim.chat.domain.repository;

import com.sparta.moim.chat.domain.model.Chat;
import java.util.List;

public interface ChatRepository {
  void save(Chat chat);

  List<Chat> findByChatRoomId(String chatRoomId);
}
