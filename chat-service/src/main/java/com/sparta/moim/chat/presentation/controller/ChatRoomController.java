package com.sparta.moim.chat.presentation.controller;

import static com.sparta.moim.chat.infrastructure.response.ChatCode.*;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.application.service.ChatRoomService;
import com.sparta.moim.chat.domain.service.ChatRoomDomainService;
import com.sparta.moim.chat.infrastructure.response.ChatCode;
import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.CommonCode;
import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  private final ChatRoomService chatRoomService;
  private final ChatRoomDomainService chatRoomDomainService;

  // 채팅방 생성하기, 관리자만 가능
  @PostMapping("")
  public ResponseEntity<ApiResponseData<ChatRoomResponseDTO>> createChatRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO){

    return ResponseEntity.ok().body(ApiResponseData.of(CHAT_ROOM_FOUND.getCode() , CHAT_ROOM_FOUND.getMessage(), chatRoomService.createChatRoomService(chatRoomRequestDTO)));
  }

  // 채팅방 조회하기, 모든 사람들이 채팅방 정보는 확인가능해야함
  // 기획상 채팅방은 현재 1개 이지만, 확장성을 고려하여 채팅방 여러개 조회할 수 있도록 구현함
  @GetMapping("/{organizationId}")
  public ResponseEntity<ApiResponseData<List<ChatRoomResponseDTO>>> readChatRoom(@PathVariable("organizationId") String organizationId){
    return ResponseEntity.ok().body(ApiResponseData.of(ChatCode.CHAT_ROOM_FOUND.getCode(), ChatCode.CHAT_ROOM_FOUND.getMessage(),chatRoomDomainService.readChatRooms(organizationId)));
  }

  // 채팅방 수정하기 - 채팅방 이름만 수정 가능함
  @PutMapping("/{chatRoomId}")
  public ResponseEntity<ApiResponseData<String>> updateChatRoom(@PathVariable("chatRoomId") String chatRoomId,
                                                                @RequestBody @Valid ChatRoomRequestDTO chatRoomRequestDTO){
    chatRoomDomainService.updateChatRoom(chatRoomId,chatRoomRequestDTO);
    return ResponseEntity.ok().body(ApiResponseData.of(CHAT_ROOM_UPDATE.getCode(), CHAT_ROOM_UPDATE.getMessage(),null));
  }

  // 채팅방 삭제하기
  @DeleteMapping("/{chatRoomId}")
  public ResponseEntity<ApiResponseData<String>> deleteChatRoom(@PathVariable("chatRoomId") String chatRoomId,
                                                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
    chatRoomDomainService.deleteChatRoom(chatRoomId, customUserDetails);
    return ResponseEntity.ok().body(ApiResponseData.of(CHAT_ROOM_DELETE.getCode(), CHAT_ROOM_DELETE.getMessage(),null));
  }

}
