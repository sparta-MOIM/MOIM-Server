package com.sparta.moim.organization.infrastruct.event;

import com.sparta.moim.organization.application.dto.event.ApplyOrganizationNotificationMessage;

public interface ApplyOrganizationNotificationProducer {
    void send(ApplyOrganizationNotificationMessage message);
}
