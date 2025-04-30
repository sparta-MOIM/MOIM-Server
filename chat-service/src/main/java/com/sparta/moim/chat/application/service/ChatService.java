package com.sparta.moim.chat.application.service;

import com.sparta.moim.chat.application.dto.ChatResponseDTO;
import com.sparta.moim.chat.application.service.kafka.ChatSender;
import com.sparta.moim.chat.domain.model.Chat;
import com.sparta.moim.chat.domain.repository.ChatRepository;
import com.sparta.moim.chat.domain.repository.ChatRoomRepository;
import com.sparta.moim.chat.infrastructure.util.ConstantUtil;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import com.sparta.moim.common.security.CustomUserDetails;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

  private final ChatSender chatSender;
  private final ChatRepository chatRepository;
  private final ChatRoomRepository chatRoomRepository;


  //채팅 메세지 전송 로직
  //유저 정보는 클라이언트에서 보내준다고 가정 (클라이언트는 캐시에서 유저 정보를 조회)
  public void sendMessage(MessageSendDTO messageSendDTO, String chatRoomId){

    // messageSendDTO에서 채팅방 번호를 가져와서 채팅방이 실제로 존재하는지 검사 (repo 구현체 단에서 예외 처리)
    chatRoomRepository.findByTrackingId(UUID.fromString(chatRoomId));

    //읽음 안읽음 숫자 처리 (나중에 로직 추가 예정)

    //조회한 유저의 닉네임, trackingId, 메세지 보낸시간을 세팅한다.
    messageSendDTO.setMessageInfo(LocalDateTime.now(), chatRoomId);

    //메세지 전송한다.
    chatSender.send(ConstantUtil.KAFKA_TOPIC_CHAT, messageSendDTO);

    log.info("카프카 토픽에 메세지 전송 완료");
    log.info("카프카 토픽 = {}",ConstantUtil.KAFKA_TOPIC_CHAT);
    log.info("채팅방 번호 = {}",chatRoomId);
    log.info("보낸 채팅 메세지 = {}", messageSendDTO);

    //메세지 전송 후, 메세지 기록을 위해서 mongodb에 메세지를 저장한다.
    chatRepository.save(messageSendDTO.toChat());

  }

  public List<ChatResponseDTO> getMessages(String chatRoomId){
    //chatRoomId의 채팅내역들을 조회
    List<Chat> chats = chatRepository.findByChatRoomId(chatRoomId);
    List<ChatResponseDTO> chatResponseDTOS = new ArrayList<>();

    for(Chat chat : chats){
        chatResponseDTOS.add(ChatResponseDTO.from(chat));
    }

    return chatResponseDTOS;
  }

}
