package com.sparta.moim.session.session.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
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


  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Member> members;

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
}
