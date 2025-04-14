package com.sparta.moim.notificationservice.application.dto.command;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import lombok.Getter;

@Getter
public class ApplyOrganizationNotificationCommand {

    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;

}