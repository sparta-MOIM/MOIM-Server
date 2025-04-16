package com.sparta.moim.chat.application.service;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.application.service.kafka.ChatRoomSender;
import com.sparta.moim.chat.domain.model.ChatRoom;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.infrastructure.util.ConstantUtil;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRoomSender chatRoomSender;

  //채팅방을 만들고 카프카에 토픽에 전달
  public void createChatRoomService(ChatRoomRequestDTO chatRoomRequestDTO){

    //모임이 존재하는지 확인 필요 (feignClient)
    //유저가 존재하는지 확인 팔요 (feignClient)

    ChatRoom chatRoom = ChatRoom.from(chatRoomRequestDTO);

    ChatRoomResponseDTO chatRoomResponseDTO = ChatRoomResponseDTO.from(chatRoomRepository.save(chatRoom).get());

    chatRoomSender.send(ConstantUtil.KAFKA_TOPIC_CHATROOM, chatRoomResponseDTO);

  }

  //

}

