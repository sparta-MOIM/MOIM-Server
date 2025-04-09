package com.moim.post.domain.vote;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.UUID;

@Entity
@Getter
@Builder
@Table(name = "p_vote")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

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

  @Column(name = "total_voter", nullable = false)
  private Integer totalVoter;

}
