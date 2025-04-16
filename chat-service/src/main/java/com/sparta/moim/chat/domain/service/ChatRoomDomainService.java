package com.sparta.moim.chat.domain.service;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomDomainService {

  private final ChatRoomRepository chatRoomRepository;

  // 채팅방 생성
  public void createChatRoomService(ChatRoomRequestDTO chatRoomRequestDTO){
    ChatRoom chatRoom = ChatRoom.from(chatRoomRequestDTO);
    chatRoomRepository.save(chatRoom);
  }

  // 채팅방 조회
  public List<ChatRoomResponseDTO> readChatRooms(String organizationId){
    List<ChatRoom> chatRooms = chatRoomRepository.readChatRooms(organizationId);
    List<ChatRoomResponseDTO> chatRoomResponseDTOS = new ArrayList<>();

    for(ChatRoom chatRoom : chatRooms){
      chatRoomResponseDTOS.add(ChatRoomResponseDTO.from(chatRoom));
    }

    return chatRoomResponseDTOS;
  }

  // 채팅방 수정
  public void updateChatRoom(String ChatRoomId, ChatRoomRequestDTO chatRoomRequestDTO){
    ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(ChatRoomId);
    chatRoom.setChatRoom(chatRoomRequestDTO.getChatRoom());
    chatRoomRepository.save(chatRoom);
  }

  //채팅방 삭제
  public void deleteChatRoom(String chatRoomId, CustomUserDetails customUserDetails){
    ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId);
    chatRoom.softDelete(customUserDetails.getTrackingId().toString());
    chatRoomRepository.save(chatRoom);
  }


}
