package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.domain.entity.NotificationTemplate;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import com.sparta.moim.notificationservice.domain.repository.NotificationTemplateRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationTemplateRepositoryImpl implements NotificationTemplateRepository {
    private final NotificationTemplateJpaRepository notificationTemplateJpaRepository;


    @Override
    public Optional<NotificationTemplate> findByNotificationType(NotificationType notificationType) {
        return notificationTemplateJpaRepository.findByNotificationType(notificationType);
    }
}
