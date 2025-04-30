package com.sparta.moim.chat.infrastructure.repository.jpa;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom,Long> {
  List<ChatRoom> findAllByOrganizationIdAndDeletedByIsNullOrderByCreatedAt(String organizationId);

  Optional<ChatRoom> findByTrackingIdAndDeletedByIsNull(UUID trackingId);

  Optional<ChatRoom> findByTrackingId(UUID trackingId);
}
