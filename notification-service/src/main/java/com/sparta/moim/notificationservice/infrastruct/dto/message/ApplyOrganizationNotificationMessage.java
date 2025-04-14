package com.sparta.moim.notificationservice.infrastruct.dto.message;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import lombok.Getter;

@Getter
public class ApplyOrganizationNotificationMessage {

    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;

}