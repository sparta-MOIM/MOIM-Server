package com.sparta.moim.notificationservice.infrastruct.dto.message;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyOrganizationNotificationMessage {

    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;
    private List<String> receiverTrackingIds;

}