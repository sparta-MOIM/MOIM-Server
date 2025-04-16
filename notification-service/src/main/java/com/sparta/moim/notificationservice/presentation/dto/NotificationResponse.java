package com.sparta.moim.notificationservice.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private String notificationTrackingId;
    private String organizationTrackingId;
    private String organizationName;
    private String content;
    private String isRead;
    private LocalDateTime receivedAt;

}
