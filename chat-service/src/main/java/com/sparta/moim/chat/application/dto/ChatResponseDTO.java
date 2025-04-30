package com.sparta.moim.chat.application.dto;

import com.sparta.moim.chat.domain.enums.MessageType;
import com.sparta.moim.chat.domain.model.Chat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponseDTO {
  private String id;
  private String content;
  private MessageType contentType;
  private String senderId;
  private String senderName;
  private String chatRoomNo;
  private LocalDateTime sendTime;

  public static ChatResponseDTO from(Chat chat){
    return ChatResponseDTO.builder()
        .id(chat.getId())
        .content(chat.getContent())
        .contentType(chat.getContentType())
        .senderId(chat.getSenderId())
        .senderName(chat.getSenderName())
        .chatRoomNo(chat.getChatRoomNo())
        .sendTime(chat.getSendTime())
        .build();
  }
}
