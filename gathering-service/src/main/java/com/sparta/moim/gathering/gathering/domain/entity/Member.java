package com.sparta.moim.gathering.gathering.domain.entity;


import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAddAdminEvent;
import com.sparta.moim.gathering.shared.enums.MemberType;
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
import java.util.HashMap;
import java.util.Map;
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

  private UUID memberId;

  @Enumerated(EnumType.STRING)
  private MemberType type;

  private LocalDateTime joinTime;

  public static Member from(GatheringAddAdminEvent gatheringAddAdminEvent) {
    return Member.builder()
        .gatheringId(gatheringAddAdminEvent.gatheringId())
        .memberId(gatheringAddAdminEvent.memberName())
        .type(MemberType.valueOf(gatheringAddAdminEvent.type()))
        .joinTime(LocalDateTime.now())
        .build();
  }

  public void changeOwner(UUID memberId) {
    this.memberId = memberId;
  }

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("gathering_id", gatheringId.toString());
    map.put("member_name", memberId.toString());
    map.put("type", MemberType.GENERAL.name());
    return map;
  }
}
