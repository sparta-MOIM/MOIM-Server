package com.sparta.moim.chat.domain.model;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  private String contentType;

  @Column(nullable = false)
  private Long senderId;

  @Column(nullable = false)
  private Integer chatRoomNo;

  @Column(nullable = false)
  private Integer senderNo;

  @Column(nullable = false)
  private String senderName;

  @Column(nullable = false)
  private LocalDateTime sendDate;

  @Column(nullable = false)
  private long readCount;



}
