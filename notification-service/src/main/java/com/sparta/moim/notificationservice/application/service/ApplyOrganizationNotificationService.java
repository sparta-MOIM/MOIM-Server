package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;

public interface ApplyOrganizationNotificationService {
    void sendApplyOrganizationNotification(ApplyOrganizationNotificationCommand command);
}
