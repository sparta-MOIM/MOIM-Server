package com.sparta.moim.notificationservice.domain.repository;

import com.sparta.moim.notificationservice.domain.entity.NotificationTemplate;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.Optional;

public interface NotificationTemplateRepository {

    Optional<NotificationTemplate> findByNotificationType(NotificationType notificationType);
}
