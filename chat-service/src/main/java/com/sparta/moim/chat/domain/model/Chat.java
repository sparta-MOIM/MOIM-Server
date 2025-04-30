package com.sparta.moim.chat.domain.model;

import com.sparta.moim.chat.domain.enums.MessageType;
import com.sparta.moim.common.utils.BaseEntity;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.mapping.Document;

// MongoDB에서 메시지 저장에 사용할 도메인 모델을 만들기
@Document(collection = "chat")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat{

  @Id
  private String id;

  @Column(columnDefinition = "TEXT",nullable = false)
  private String content;

  private MessageType contentType;

  //메세지를 보낸사람의 trackingId
  @Column(nullable = false)
  private String senderId;

  //특정 모임의 닉네임을 전달
  @Column(nullable = false)
  private String senderName;

  @Column(length = 36, nullable = false, unique = true)
  private String chatRoomNo;

  @Column(nullable = false)
  private LocalDateTime sendTime;

  // 읽음 인읽음 처리 (추후 기능 추가 예정)
  private long readCount;

}
