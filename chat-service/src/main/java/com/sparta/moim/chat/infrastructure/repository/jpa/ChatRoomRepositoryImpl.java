package com.sparta.moim.chat.infrastructure.repository.jpa;

import static com.sparta.moim.chat.infrastructure.response.ChatCode.*;

import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.infrastructure.response.ChatCode;
import com.sparta.moim.common.exception.BaseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
  private final JpaChatRoomRepository jpaChatRoomRepository;

  @Override
  public ChatRoom save(ChatRoom chatRoom) {
    return jpaChatRoomRepository.save(chatRoom);
  }

  @Override
  public List<ChatRoom> readChatRooms(String organization_id){
    return jpaChatRoomRepository.findAllByOrganizationIdAndDeletedByIsNullOrderByCreatedAt(organization_id);
  }

  @Override
  public ChatRoom findByChatRoomId(String chatRoomId){
    return jpaChatRoomRepository.findByTrackingIdAndDeletedByIsNull(UUID.fromString(chatRoomId))
        .orElseThrow(() -> new BaseException(CHAT_NOT_FOUND));
  }

  @Override
  public ChatRoom findByTrackingId(UUID trackingId){
    return jpaChatRoomRepository.findByTrackingId(trackingId).orElseThrow(() -> new BaseException(
        CHAT_NOT_FOUND));
  }


}
