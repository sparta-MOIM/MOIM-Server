package com.sparta.moim.organization.infrastruct.event;

import com.sparta.moim.organization.application.dto.message.ApplyOrganizationNotificationMessage;

public interface ApplyOrganizationNotificationProducer {
    void send(ApplyOrganizationNotificationMessage message);
}
