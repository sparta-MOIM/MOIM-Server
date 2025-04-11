package com.sparta.moim.chat.application.service.kafka;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomSender {
  private final KafkaTemplate<String, ChatRoomResponseDTO> kafkaTemplate;

  public void send(String topic, ChatRoomResponseDTO chatRoomResponseDTO) {
    log.info("chatRoomResponseDTO = {}", chatRoomResponseDTO);
    // KafkaTemplate을 사용하여 메시지를 지정된 토픽으로 전송
    kafkaTemplate.send(topic, chatRoomResponseDTO);
  }
}
