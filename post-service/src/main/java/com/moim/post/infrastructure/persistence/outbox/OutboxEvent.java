package com.moim.post.infrastructure.persistence.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@Table(name = "p_outbox_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEvent {

  @Id
  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false, unique = true)
  private UUID id;

  @Column(name = "aggregate_type",nullable = false)
  private String aggregateType;

  @Column(name = "aggregate_id",nullable = false)
  private String aggregateId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type",nullable = false)
  private OutBoxEventType type;

  @Column(name = "payload", columnDefinition = "TEXT")
  private String payload;

  @Column(name = "created_at",nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "is_processed",nullable = false)
  private Boolean isProcessed;

  public static OutboxEvent create(
      String aggregateType,
      String aggregateId,
      String type,
      String payload
  ) {
    return OutboxEvent.builder()
        .aggregateType(aggregateType)
        .aggregateId(aggregateId)
        .type(OutBoxEventType.valueOf(type))
        .payload(payload)
        .createdAt(LocalDateTime.now())
        .isProcessed(false)
        .build();
  }

  public void markAsProcessed() {
    this.isProcessed = true;
  }
}
