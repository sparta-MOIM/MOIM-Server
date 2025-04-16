package com.sparta.moim.chat.presentation.controller;

import com.sparta.moim.chat.application.service.ChatService;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  // /send/message로 발행된 메세지 처리
  // 메세지 발행주소 prefix를 제외한 형태
  @MessageMapping("/message/{chatRoomId}")
  @SendTo("/room/{chatRoomId}")
  public void sendMessage(@Valid MessageSendDTO messageSendDTO,  @DestinationVariable("chatRoomId") Integer chatRoomId){
    chatService.sendMessage(messageSendDTO, chatRoomId);
  }

}
