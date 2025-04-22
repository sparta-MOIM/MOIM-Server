package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.sparta.moim.notificationservice.application.service.ApplyOrganizationNotificationService;
import com.sparta.moim.notificationservice.application.service.NotificationHandler;
import com.sparta.moim.notificationservice.infrastruct.dto.message.ApplyOrganizationNotificationMessage;
import com.sparta.moim.notificationservice.infrastruct.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationConsumer implements NotificationConsumer{

    private final ApplyOrganizationNotificationService applyOrganizationNotificationService;
    private final CommandMapper commandMapper;

    @Override
    @KafkaListener(topics = "organization-apply-notification", groupId = "notification-group")
    public void consume(String rawMessage) {
            log.info("message received : {}", rawMessage);
            applyOrganizationNotificationService.sendApplyOrganizationNotification(rawMessage);
            log.info("알림 전송 완료");
    }
}
