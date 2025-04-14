package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.notificationservice.application.service.ApplyOrganizationNotificationService;
import com.sparta.moim.notificationservice.infrastruct.dto.message.ApplyOrganizationNotificationMessage;
import com.sparta.moim.notificationservice.infrastruct.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationConsumerImpl implements ApplyOrganizationNoficiationConsumer{

    private final ApplyOrganizationNotificationService applyOrganizationNotificationService;
    private final CommandMapper commandMapper;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "organization-apply-notification", groupId = "notification-group")
    public void consume(ApplyOrganizationNotificationMessage message) {
            log.info("message received : {}", message);
            applyOrganizationNotificationService.sendApplyOrganizationNotification(commandMapper.toCommand(message));
    }
}
