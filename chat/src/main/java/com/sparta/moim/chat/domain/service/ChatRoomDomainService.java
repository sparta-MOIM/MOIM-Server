package com.sparta.moim.chat.domain.service;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.exception.BaseException;
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
  public List<ChatRoomResponseDTO> readChatRooms(Long organization_id){
    List<ChatRoom> chatRooms = chatRoomRepository.readChatRooms(organization_id);
    List<ChatRoomResponseDTO> chatRoomResponseDTOS = new ArrayList<>();

    for(ChatRoom chatRoom : chatRooms){
      chatRoomResponseDTOS.add(ChatRoomResponseDTO.from(chatRoom));
    }

    return chatRoomResponseDTOS;
  }

  // 채팅방 수정
  public void updateChatRoom(Long chat_room_id, ChatRoomRequestDTO chatRoomRequestDTO){
    ChatRoom chatRoom = chatRoomRepository.findById(chat_room_id).orElseThrow(()->new BaseException("해당 채팅방이 존재하지 않습니다."));
    chatRoom.setChatRoom(chatRoomRequestDTO.getChat_room());
    chatRoomRepository.save(chatRoom).orElseThrow(()->new BaseException("채팅방 수정에 실패하였습니다."));
  }

  //채팅방 삭제
  public void deleteChatRoom(Long chat_room_id){
    ChatRoom chatRoom = chatRoomRepository.findById(chat_room_id).orElseThrow(()->new BaseException("해당 채팅방이 존재하지 않습니다."));
    chatRoom.softDelete("testuser");
    chatRoomRepository.save(chatRoom).orElseThrow(()->new BaseException("채팅방 삭제에 실패하였습니다."));
  }


}
