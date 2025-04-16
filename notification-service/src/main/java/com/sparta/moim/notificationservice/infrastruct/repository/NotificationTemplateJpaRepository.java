package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.domain.entity.NotificationTemplate;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateJpaRepository extends JpaRepository<NotificationTemplate, Long> {
    Optional<NotificationTemplate> findByNotificationType(NotificationType notificationType);
}
