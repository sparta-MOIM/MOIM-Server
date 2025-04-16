package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
}
