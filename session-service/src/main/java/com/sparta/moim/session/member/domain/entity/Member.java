package com.sparta.moim.session.member.domain.entity;

import com.sparta.moim.session.member.domain.enums.MemberType;
import com.sparta.moim.session.shared.dto.SharedSessionMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Getter
@Table(name = "p_session_member")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String memberName;

  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false)
  private UUID sessionId;

  @Enumerated(EnumType.STRING)
  private MemberType type;

  public static Member from(SharedSessionMember sharedSessionMember) {
    return Member.builder()
        .memberName(sharedSessionMember.memberName())
        .sessionId(sharedSessionMember.sessionId())
        .type(MemberType.valueOf(sharedSessionMember.type()))
        .build();
  }

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("session_id", sessionId.toString());
    map.put("member_id", memberName);
    map.put("type", MemberType.GENERAL.name());
    return map;
  }
}
