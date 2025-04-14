package com.sparta.moim.organization.infrastruct.event;

import com.sparta.moim.organization.application.dto.message.ApplyOrganizationNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyOrganizationNotificationProducerImpl implements ApplyOrganizationNotificationProducer {

    private final KafkaTemplate<String, ApplyOrganizationNotificationMessage> kafkaTemplate;
    private static final String TOPIC = "organization-apply-notification";

    @Override
    public void send(ApplyOrganizationNotificationMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
