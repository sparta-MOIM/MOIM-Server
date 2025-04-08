package com.sparta.moim.chat.domain.model;

import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name="chat_room")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String chatRoom;

  private Long organizationId;

  public static ChatRoom from(ChatRoomRequestDTO chatRoomRequestDTO){
    return ChatRoom.builder()
        .chatRoom(chatRoomRequestDTO.getChat_room())
        .organizationId(chatRoomRequestDTO.getOrganization_id())
        .build();
  }

}
