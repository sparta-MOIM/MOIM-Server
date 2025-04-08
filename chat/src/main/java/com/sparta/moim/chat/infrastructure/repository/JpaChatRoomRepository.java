package com.sparta.moim.chat.infrastructure.repository;

import com.sparta.moim.chat.domain.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
