package com.sparta.moim.chat.application.service.kafka;

import com.sparta.moim.chat.infrastructure.util.ConstantUtil;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatReceiver {
  private final SimpMessageSendingOperations template;

  // 카프카 message consume 로직
  @KafkaListener(groupId = ConstantUtil.KAFKA_GROUP_ID_CHAT, topics = ConstantUtil.KAFKA_TOPIC_CHAT, containerFactory = "kafkaMessageSendContainerFactory")
  public void receiveMessage(MessageSendDTO messageSendDTO){
    log.info("================================ 카프카 리스너 동작 =========================================");
    log.info("메세지 전송 위치 = /room/" + messageSendDTO.getChatRoomNo());
    log.info("채팅방으로 해당 메세지 전송 : {}", messageSendDTO);

    // 보낸 메세지 (messageSendDTO)에서 채팅방의 번호를 사용해서, 해당 채팅방 구독자들에게 메세지를 전달한다.
    template.convertAndSend("/room/" + messageSendDTO.getChatRoomNo(),messageSendDTO);
  }

}
