package com.sparta.moim.notificationservice.application.dto.command;

import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyOrganizationNotificationCommand {

    private NotificationType notificationType;
    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;

    public Map<String, String> toPlaceholderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("organizationName", organizationName);
        return map;
    }

}