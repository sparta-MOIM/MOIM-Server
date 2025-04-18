package com.sparta.moim.chat.presentation.controller;

import static com.sparta.moim.chat.infrastructure.response.ChatCode.*;

import com.sparta.moim.chat.application.dto.ChatResponseDTO;
import com.sparta.moim.chat.application.service.ChatService;
import com.sparta.moim.chat.infrastructure.response.ChatCode;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  // 메세지 보내기
  // /send/message로 발행된 메세지 처리
  // 메세지 발행주소 prefix를 제외한 형태
  //채팅을 보낼때는 클라이언트가 유저의 trackingId, nickname 등 유저에 대한 정보를 갖고있다고 가정
  @MessageMapping("/message/{chatRoomId}")
  @SendTo("/room/{chatRoomId}")
  public void sendMessage(@Valid MessageSendDTO messageSendDTO, @DestinationVariable("chatRoomId") String chatRoomId){
    chatService.sendMessage(messageSendDTO, chatRoomId);
  }


  //채팅 내역 조회
  //전체 채팅 내역 조회 OR 일부 채팅 내역 조회
  @GetMapping("/api/v1/chat/{chatRoomId}")
  public ResponseEntity<ApiResponseData<List<ChatResponseDTO>>> getChats(@PathVariable("chatRoomId") String chatRoomId){
    return ResponseEntity.ok().body(ApiResponseData.of(CHAT_FOUND.getCode(),CHAT_FOUND.getMessage(),chatService.getMessages(chatRoomId)));
  }

}
