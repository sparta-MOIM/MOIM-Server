package com.sparta.moim.session.session.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.session.shared.enums.SessionStatus;
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
public class Session extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false, length = 100)
  private String organizationId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 100)
  private String publisher;

  private Integer count;

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


  public void update(Session updateSessionInfo) {
    this.title = updateSessionInfo.title == null ? this.title : updateSessionInfo.title;
    this.count = updateSessionInfo.count == null ? this.count : updateSessionInfo.count;
    this.status = updateSessionInfo.status == null ? this.status : updateSessionInfo.status;
  }

  public void stateChange(SessionStatus sessionStatus) {
    this.status = sessionStatus;
  }

  public void confirm() {
    this.confirmTime = LocalDateTime.now();
    this.status = SessionStatus.OPEN;
  }
  // open,close 시간은 현재시간보다 이전일 수 없다
  // close는 open보다 이전일 수 없습니다.
  public void timeValidate() {
    LocalDateTime now = LocalDateTime.now();
    openTimeValidate(now);
    openTimeBeforeCloseTimeValidate();
  }

  private void openTimeBeforeCloseTimeValidate() {
    if(openTime.isAfter(closeTime)){
      throw new IllegalArgumentException("Session close time is after open time");
    }
  }

  private void openTimeValidate(LocalDateTime now) {
    if(openTime.isAfter(now)){
      throw new IllegalArgumentException("Session open time is after current time");
    }
  }
}
