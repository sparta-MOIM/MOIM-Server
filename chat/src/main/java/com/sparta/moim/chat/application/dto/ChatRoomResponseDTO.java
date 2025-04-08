package com.sparta.moim.chat.application.dto;

import com.sparta.moim.chat.domain.model.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
