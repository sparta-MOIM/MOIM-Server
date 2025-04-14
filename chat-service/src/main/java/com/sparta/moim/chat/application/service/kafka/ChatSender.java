package com.sparta.moim.chat.application.service.kafka;

import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatSender {
  private final KafkaTemplate<String, MessageSendDTO> kafkaTemplate;

  public void send(String topic, MessageSendDTO messageSendDTO){
    kafkaTemplate.send(topic, messageSendDTO);
  }

}
