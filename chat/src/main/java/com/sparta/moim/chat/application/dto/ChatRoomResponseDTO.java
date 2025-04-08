package com.sparta.moim.chat.application.dto;

import com.sparta.moim.chat.domain.model.ChatRoom;
import lombok.Builder;

@Builder
public class ChatRoomResponseDTO {
  private String chatRoom;
  private Long organizationId;

  public static ChatRoomResponseDTO from(ChatRoom chatRoom){
    return ChatRoomResponseDTO.builder()
        .chatRoom(chatRoom.getChatRoom())
        .organizationId(chatRoom.getOrganizationId())
        .build();
  }
}
