package com.sparta.moim.gathering.gathering.domain.entity;

import com.sparta.moim.gathering.gathering.domain.enums.MemberType;
import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAdminSaveEvent;
import jakarta.persistence.Column;
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
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "p_gathering_member")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Getter
@Builder
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false)
  private UUID gatheringId;

  private String memberId;

  private MemberType type;

  private LocalDateTime joinTime;

  public static Member from(GatheringAdminSaveEvent gatheringAdminSaveEvent) {
    return Member.builder()
        .gatheringId(gatheringAdminSaveEvent.gatheringId())
        .memberId(gatheringAdminSaveEvent.memberName())
        .type(MemberType.valueOf(gatheringAdminSaveEvent.type()))
        .joinTime(LocalDateTime.now())
        .build();
  }

  public void changeOwner(String memberId) {
    this.memberId = memberId;
  }
}
