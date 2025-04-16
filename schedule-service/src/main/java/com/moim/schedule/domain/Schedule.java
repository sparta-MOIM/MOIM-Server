package com.moim.schedule.domain;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@Getter
@Builder
@Table(name = "p_schedule")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
  private UUID trackingId;

  @Column(name = "organization_id", nullable = false)
  private UUID organizationId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Embedded
  @Column(name = "period", nullable = false)
  private Period period;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted;

  public static Schedule create(
      UUID organizationId,
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end
  ) {
    return Schedule.builder()
        .organizationId(organizationId)
        .title(title)
        .content(content)
        .period(new Period(start, end))
        .isDeleted(false)
        .build();
  }

  public void updateTitle(String title){
    this.title = title;
  }

  public void updateContent(String content){
    this.content = content;
  }

  public void updateStart(LocalDateTime start){
    period.updateStart(start);
  }

  public void updateEnd(LocalDateTime end){
    period.updateEnd(end);
  }

  public void delete(){
    isDeleted = true;
  }

}
