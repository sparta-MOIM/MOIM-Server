package com.sparta.moim.notificationservice.infrastruct.dto.message;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.List;

public class ChatNotificationMessage {
    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String sender;
    private List<String> receiverTrackingIds;
}
