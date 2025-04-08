package com.sparta.moim.chat.presentation.controller;

import com.sparta.moim.chat.domain.service.ChatRoomDomainService;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatRoomController {

  private final ChatRoomDomainService chatRoomDomainService;

  //채팅방 생성하기, 관리자만 가능
  @PostMapping("/chat_room")
  public ResponseEntity<ApiResponseData<String>> createChatRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO){

    chatRoomDomainService.createChatRoomService(chatRoomRequestDTO);

    return ResponseEntity.ok().body(ApiResponseData.success("채팅방 생성이 정상적으로 처리되었습니다."));
  }

}
