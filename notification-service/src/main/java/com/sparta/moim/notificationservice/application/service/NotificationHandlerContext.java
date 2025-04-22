package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;

public interface NotificationHandlerContext {
    void handle(NotificationType type, String rawJson);
}
