package com.sparta.moim.session.session.domain.entity;

import com.sparta.moim.session.session.domain.enums.SessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
@Table(name = "p_session")
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false, length = 100)
  private String organizationId;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String name;

  @Column(nullable = false, length = 100)
  private String publisher;

  private int count;

  private LocalDateTime openTime;

  private LocalDateTime closeTime;

  @Enumerated(EnumType.STRING)
  private SessionStatus status;


  private LocalDateTime applyTime;

  private LocalDateTime confirmTime;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String reason;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false, unique = true)
  private UUID trackingId;

}
