package com.moim.post.infrastructure.persistence.repository.mongo;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "processed_message")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessedMessage {

  @Id
  private String id;

  private UUID eventId;

  private LocalDateTime createdAt;

  public static ProcessedMessage create(
     UUID eventId
  ) {
    return ProcessedMessage.builder()
        .eventId(eventId)
        .createdAt(LocalDateTime.now())
        .build();
  }
}

