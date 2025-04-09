package com.moim.post.domain.feed;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
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
@Getter
@Builder
@Table(name = "p_feed")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseEntity {

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

  @Column(name = "context", nullable = false)
  private String context;

  @Column(name = "image_url")
  private String imageUrl;

  @ElementCollection
  @JdbcTypeCode(Types.VARCHAR)
  @Column(name = "tagged_user_ids", nullable = false)
  private List<UUID> taggedUserIds;
}
