package com.sparta.moim.chat.application.dto;

import com.sparta.moim.chat.domain.model.ChatRoom;
import java.util.UUID;
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
  private String organizationId;
  private String createUser;
  private String trackingId;


  public static ChatRoomResponseDTO from(ChatRoom chatRoom){
    return ChatRoomResponseDTO.builder()
        .chatRoom(chatRoom.getChatRoom())
        .organizationId(chatRoom.getOrganizationId())
        .createUser(chatRoom.getCreateUser())
        .trackingId(chatRoom.getTrackingId().toString())
        .build();
  }
}
