package com.sparta.moim.gathering.gathering.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.GatheringEventCriteria;
import com.sparta.moim.gathering.shared.enums.EventType;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "p_gathering_outbox")
@Builder
public class OutboxEvent extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stream_key", nullable = false)
  private String streamKey;

  @Column(name = "event_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Lob
  @Column(nullable = false)
  private String payload;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OutboxType status;

  @Column(name = "retry_count", nullable = false)
  private int retryCount = 0;

  @Column(name = "last_attempt_time")
  private LocalDateTime lastAttemptTime;

  public static OutboxEvent create(GatheringEventCriteria criteria) {
    return OutboxEvent.builder()
        .streamKey(criteria.streamJoinKey())
        .eventType(criteria.eventType())
        .payload(criteria.payload())
        .status(OutboxType.PENDING)
        .build();
  }

  public void markAsSent() {
    this.status = OutboxType.SENT;
  }

  public void markAsFailed() {
    this.retryCount++;
    this.lastAttemptTime = LocalDateTime.now();
    this.status = OutboxType.FAILED;
  }

  public boolean canRetry(int maxRetries) {
    return this.retryCount < maxRetries;
  }

}
