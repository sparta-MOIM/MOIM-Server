package com.sparta.moim.notificationservice.application.dto.query;

import com.sparta.moim.notificationservice.domain.entity.Notification;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetNotificationQuery {

    private String notificationTrackingId;
    private String organizationTrackingId;
    private String organizationName;
    private String content;
    private Boolean isRead;
    private LocalDateTime receivedAt;

    public static GetNotificationQuery from(Notification notification){
        return GetNotificationQuery
                .builder()
                .notificationTrackingId(notification.getTrackingId().toString())
                .organizationTrackingId(notification.getAccessTrackingId().toString())
                .isRead(notification.getIsRead())
                .content(notification.getContent())
                .receivedAt(notification.getCreatedAt())
                .build();
    }
}
