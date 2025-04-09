package com.sparta.moim.chat.infrastructure.repository.jpa;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom,Long> {
  List<ChatRoom> findAllByOrganizationIdAndDeletedByIsNullOrderByCreatedAt(Long organizationId);

  Optional<ChatRoom> findByIdAndDeletedByIsNull(Long id);
}
