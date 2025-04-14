package com.sparta.moim.organization.infrastruct.adaptor.out;

import com.sparta.moim.organization.application.dto.message.ApplyOrganizationNotificationMessage;

public interface ApplyOrganizationNotificationProducer {
    void send(ApplyOrganizationNotificationMessage message);
}
