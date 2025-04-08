package com.sparta.moim.gathering.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "p_gathering")
@NoArgsConstructor
public class Gathering extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String organizationId;

  private String owner;

  private int count;

  private Boolean status;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false, unique = true)
  private UUID trackingId;

  public static Gathering create(String organizationId, String name, String owner, int count, boolean status) {
    return new Gathering(null, organizationId, name, owner, count, status);
  }

  public static Gathering update(Long gatheringId, String name, int count, Boolean status) {
    return new Gathering(gatheringId, null, name, null, count, status);
  }

  private Gathering(Long id, String organizationId, String name, String owner, int count, Boolean status) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
    this.owner = owner;
    this.count = count;
    this.status = status;
  }

  public void change(Gathering updatedGathering) {
    this.name = updatedGathering.name == null ? this.name : updatedGathering.name;
    this.count = updatedGathering.count == 0 ? this.count : updatedGathering.count;
    this.status = updatedGathering.status == null ? this.status : updatedGathering.status;
  }
}
