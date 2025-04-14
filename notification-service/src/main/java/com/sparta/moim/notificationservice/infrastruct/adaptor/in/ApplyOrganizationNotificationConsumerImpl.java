package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.sparta.moim.notificationservice.infrastruct.dto.message.ApplyOrganizationNotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "organization-apply-notification", groupId = "notification-group")
public class ApplyOrganizationNotificationConsumerImpl implements ApplyOrganizationNoficiationConsumer{

    @Override
    public void consume(ApplyOrganizationNotificationMessage message) {

    }
}
