package com.sparta.moim.gathering.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "p_gathering")
@NoArgsConstructor
public class Gathering {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
  private UUID id;

  private String name;

  private String organizationId;

  private String owner;

  private int count;

  private boolean status;


  public static Gathering create(String organizationId, String name, String owner, int count, boolean status) {
    return new Gathering(null, organizationId, name, owner, count, status);
  }

  private Gathering(UUID id, String organizationId, String name, String owner, int count, boolean status) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
    this.owner = owner;
    this.count = count;
    this.status = status;
  }

}
