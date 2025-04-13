package com.sparta.moim.chat.application.service;

import com.sparta.moim.chat.application.service.kafka.ChatSender;
import com.sparta.moim.chat.infrastructure.util.ConstantUtil;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatSender chatSender;

  //채팅 메세지 전송 로직
  public void sendMessage(MessageSendDTO messageSendDTO, String userTrackingId){
    //request header나 customuserdetails에서 가져온 trackingId를 바탕으로 유저를 조회
    //유저 조회 feignClient 필요

    //읽음 안읽음 숫자 처리 (나중에 로직 추가 예정)

    //조회한 유저의 닉네임, trackingId, 메세지 보낸시간을 세팅한다.
    messageSendDTO.setSendTimeAndSender(LocalDateTime.now(), userTrackingId, "조회한 유저의 닉네임");

    //메세지 전송한다.
    chatSender.send(ConstantUtil.KAFKA_TOPIC_CHAT, messageSendDTO);

  }

}
