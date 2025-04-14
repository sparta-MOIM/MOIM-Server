package com.sparta.moim.chat.presentation.controller;

import com.sparta.moim.chat.application.service.ChatService;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @MessageMapping("/message")
  public void sendMessage(@Valid MessageSendDTO messageSendDTO, @RequestHeader("X-User-ID") String userTrackingId){
    chatService.sendMessage(messageSendDTO,userTrackingId);
  }

}
