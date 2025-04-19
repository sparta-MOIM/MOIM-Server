package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.exception.CannotFindNotification;
import com.sparta.moim.notificationservice.application.usecase.ReadNotificationUseCase;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadNotificationService implements ReadNotificationUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public void execute(String userTrackingId, String notificationTrackingId) {
        Notification notification = notificationRepository.findByUserTrackingIdAndNotificationTrackingId(
                userTrackingId, notificationTrackingId).orElseThrow(CannotFindNotification::new);
        notification.updateIsRead(true);
        notificationRepository.save(notification);
    }
}
