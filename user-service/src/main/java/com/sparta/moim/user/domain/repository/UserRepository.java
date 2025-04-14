package com.sparta.moim.user.domain.repository;

import com.sparta.moim.user.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  Optional<User> findByTrackingIdAndDeletedAtIsNull(UUID trackingId);
}
