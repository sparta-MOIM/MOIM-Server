package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;

public interface NotificationHandlerStrategy {
    NotificationType getType(); // 이 핸들러가 처리할 타입
    void handleNotification(String rawMessage);
}