package com.sparta.moim.chat.domain.repository;

import com.sparta.moim.chat.domain.model.Chat;

public interface ChatRepository {
  void save(Chat chat);
}
