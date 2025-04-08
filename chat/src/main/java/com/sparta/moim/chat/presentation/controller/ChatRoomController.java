package com.sparta.moim.chat.presentation.controller;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.domain.service.ChatRoomDomainService;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.Code;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat_room")
@RequiredArgsConstructor
public class ChatRoomController {

  private final ChatRoomDomainService chatRoomDomainService;

  //채팅방 생성하기, 관리자만 가능
  @PostMapping("")
  public ResponseEntity<ApiResponseData<String>> createChatRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO){

    chatRoomDomainService.createChatRoomService(chatRoomRequestDTO);

    return ResponseEntity.ok().body(ApiResponseData.success("채팅방 생성이 정상적으로 처리되었습니다."));
  }

  // 채팅방 조회하기, 모든 사람들이 채팅방 정보는 확인가능해야함
  // 기획상 채팅방은 현재 1개 이지만, 확장성을 고려하여 채팅방 여러개 조회할 수 있도록 구현함
  @GetMapping("/{organization_id}")
  public ResponseEntity<ApiResponseData<List<ChatRoomResponseDTO>>> readChatRoom(@PathVariable("organization_id") Long organization_id){
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(), "채팅방 조회가 성공적으로 완료되었습니다.",chatRoomDomainService.readChatRooms(organization_id)));
  }

  //채팅방 수정하기 - 채팅방 이름만 수정 가능함
  @PutMapping("/{chat_room_id}")
  public ResponseEntity<ApiResponseData<String>> updateChatRoom(@PathVariable("chat_room_id") Long chat_room_id,
                                                                @RequestBody ChatRoomRequestDTO chatRoomRequestDTO){
    chatRoomDomainService.updateChatRoom(chat_room_id,chatRoomRequestDTO);
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(), "채팅방 수정을 성공하였습니다.",null));
  }

  @DeleteMapping("/{chat_room_id}")
  public ResponseEntity<ApiResponseData<String>> deleteChatRoom(@PathVariable("chat_room_id") Long chat_room_id){
    chatRoomDomainService.deleteChatRoom(chat_room_id);
    return ResponseEntity.ok().body(ApiResponseData.of(Code.SUCCESS.getCode(), "채팅방 삭제를 성공하였습니다.",null));

  }

}
