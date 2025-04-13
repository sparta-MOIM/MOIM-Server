package com.sparta.moim.organization.application.dto.event;

import com.sparta.moim.organization.application.enums.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ApplyOrganizationNotificationMessage {

    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;

    public static ApplyOrganizationNotificationMessage of(
            NotificationType notificationType,
            String organizationTrackingId,
            String organizationName,
            String userTrackingId,
            String username) {
        return ApplyOrganizationNotificationMessage.builder()
                .notificationType(notificationType)
                .organizationTrackingId(organizationTrackingId)
                .organizationName(organizationName)
                .userTrackingId(userTrackingId)
                .username(username)
                .build();
    }
}