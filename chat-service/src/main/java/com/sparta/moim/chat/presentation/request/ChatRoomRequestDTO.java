package com.sparta.moim.chat.presentation.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChatRoomRequestDTO {

  //채팅방 이름
  @NotNull
  private String chatRoom;

  //모임 trackingId
  @NotNull
  private String organizationId;

  //채팅방을 생성한 사람 (모임 닉네임)
  @NotNull
  private String createUser;
}
