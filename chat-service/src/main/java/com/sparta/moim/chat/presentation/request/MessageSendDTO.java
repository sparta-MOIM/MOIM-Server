package com.sparta.moim.chat.presentation.request;

import com.sparta.moim.chat.domain.model.Chat;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

// Kafka에서 메시지 전달에 사용할 도메인 모델 작성
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDTO implements Serializable {

  @NotNull
  private Integer chatNo;

  @NotNull
  private String contentType;

  @NotNull
  private String content;

  private String senderName;

  private String senderId;

  private long sendTime;
  private Integer readCount;

  public void setSendTimeAndSender(LocalDateTime sendTime, String senderId, String senderName) {
    this.senderName = senderName;
    this.sendTime = sendTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    this.senderId = senderId;
    //this.readCount = readCount;
  }

  public Chat convertEntity() {
    return Chat.builder()
        .senderName(senderName)
        .senderId(senderId)
        .chatRoomNo(chatNo)
        .contentType(contentType)
        .content(content)
        .sendDate(Instant.ofEpochMilli(sendTime).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
        .readCount(readCount)
        .build();
  }
}
