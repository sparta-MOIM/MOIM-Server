package com.sparta.moim.chat.infrastructure.repository.jpa;

import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.common.exception.BaseException;
import java.util.List;
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

  @Override
  public List<ChatRoom> readChatRooms(Long organization_id){
    return jpaChatRoomRepository.findAllByOrganizationIdAndDeletedByIsNullOrderByCreatedAt(organization_id);
  }

  @Override
  public Optional<ChatRoom> findById(Long chat_room_id){
    return Optional.ofNullable(jpaChatRoomRepository.findByIdAndDeletedByIsNull(chat_room_id)
        .orElseThrow(() -> new BaseException("해당 채팅방을 찾지 못하였습니다.")));
  }


}
