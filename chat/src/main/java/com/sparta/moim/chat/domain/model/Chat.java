package com.sparta.moim.chat.domain.model;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseEntity {
  @Id
  private String id;
  @Column(columnDefinition = "TEXT",nullable = false)
  private String content;

  @Column(nullable = false)
  private Long userId;

}
