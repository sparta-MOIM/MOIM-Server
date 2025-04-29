package com.sparta.moim.organization.application.dto.message;

import com.sparta.moim.organization.application.enums.NotificationType;
import java.util.List;
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

    private String organizationTrackingId;
    private String organizationName;
    private String userTrackingId;
    private String username;
    private List<String> receiverTrackingIds;

    public static ApplyOrganizationNotificationMessage of(
            String organizationTrackingId,
            String organizationName,
            String userTrackingId,
            String username,
            List<String> receiverTrackingIds) {
        return ApplyOrganizationNotificationMessage.builder()
                .organizationTrackingId(organizationTrackingId)
                .organizationName(organizationName)
                .userTrackingId(userTrackingId)
                .username(username)
                .receiverTrackingIds(receiverTrackingIds)
                .build();
    }
}