package com.sparta.moim.user.domain.model;

import static com.sparta.moim.user.domain.model.UserRole.USER;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
  private UUID trackingId;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String hashedPassword;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public static User createUser(String username, String encodedPassword, String name) {
    return User.builder()
        .username(username)
        .hashedPassword(encodedPassword)
        .name(name)
        .role(USER)
        .build();
  }

  @Builder
  private User(
      String username,
      String hashedPassword,
      String name,
      UserRole role
  ) {
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.name = name;
    this.role = role;
  }
}
