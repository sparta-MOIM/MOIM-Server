package com.sparta.moim.notificationservice.application.usecase;

public interface ReadNotificationUseCase {
    void execute(String userTrackingId, String notificationTrackingId);
}
