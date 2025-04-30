package com.sparta.moim.chat.presentation.request;

import com.sparta.moim.chat.domain.enums.MessageType;
import com.sparta.moim.chat.domain.model.Chat;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

// 메세지 내용
// Kafka로 메시지를 전달할 때 사용할 도메인 모델 작성
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDTO implements Serializable {

  @NotNull
  private String content;

  @NotNull
  private MessageType contentType; //enum은 암묵적으로 Serializable를 구현한다.

  //메세지를 보낸사람의 trackingId
  private String senderId;

  private String senderName;

  private String chatRoomNo;

  private Long sendTime;

  private Integer readCount;

  //유저의 권한 검사를 위해 Role 필드 추가 필요

  public void setMessageInfo(LocalDateTime sendTime, String chatRoomId) {
    this.chatRoomNo = chatRoomId;
    //대한민국 시간대 저장 (나라별 시간대 관리)
    //직렬화를 편하게 하기 위해서 추가, LocalDateTime을 사용하면 커스텀 직렬화/역직렬화 필요
    this.sendTime = sendTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
  }

  public Chat toChat() {
    return Chat.builder()
        .content(content)
        .contentType(contentType)
        .senderId(senderId)
        .senderName(senderName)
        .chatRoomNo(chatRoomNo)
        //db에 저장할 때는 보기 편하게, LocalDateTime으로 설정
        .sendTime(Instant.ofEpochMilli(sendTime).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
        //.readCount(readCount)
        .build();
  }
}
