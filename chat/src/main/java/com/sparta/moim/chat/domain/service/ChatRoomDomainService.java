package com.sparta.moim.chat.domain.service;

import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomDomainService {

  private final ChatRoomRepository chatRoomRepository;

  public void createChatRoomService(ChatRoomRequestDTO chatRoomRequestDTO){

    ChatRoom chatRoom = ChatRoom.from(chatRoomRequestDTO);
    chatRoomRepository.save(chatRoom);

  }



}
