package com.sparta.moim.chat.application.service;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;

  //채팅방을 만들고 카프카에 토픽에 전달
  //모임과, 유저에 대한 정보는 클라이언트가 갖고있다는 것으로 가정 (캐싱으로 조회)
  public ChatRoomResponseDTO createChatRoomService(ChatRoomRequestDTO chatRoomRequestDTO){
    ChatRoom chatRoom = ChatRoom.from(chatRoomRequestDTO);
    return ChatRoomResponseDTO.from(chatRoomRepository.save(chatRoom));
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

