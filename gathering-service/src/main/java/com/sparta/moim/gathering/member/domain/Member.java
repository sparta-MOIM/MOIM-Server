package com.sparta.moim.gathering.member.domain;

import com.sparta.moim.gathering.member.domain.enums.MemberType;
import com.sparta.moim.gathering.shared.dto.SharedGatheringMember;
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

  /**
   * SharedGatheringMember DTO로부터 Member 엔티티 인스턴스를 생성합니다.
   *
   * @param sharedGatheringMember 변환할 SharedGatheringMember 객체
   * @return 변환된 Member 엔티티 인스턴스
   */
  public static Member from(SharedGatheringMember sharedGatheringMember) {
    return Member.builder()
        .gatheringId(sharedGatheringMember.gatheringId())
        .memberId(sharedGatheringMember.memberName())
        .type(MemberType.valueOf(sharedGatheringMember.type()))
        .joinTime(LocalDateTime.now())
        .build();
  }
}
