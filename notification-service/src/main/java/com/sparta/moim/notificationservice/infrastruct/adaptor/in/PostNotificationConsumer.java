package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.sparta.moim.notificationservice.application.service.NotificationHandlerContext;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostNotificationConsumer implements NotificationConsumer {

    private final NotificationHandlerContext notificationHandlerContext;

    @Override
    @KafkaListener(topics = "unread-post", groupId = "notification-group", containerFactory = "stringContainerFactory")
    public void consume(String rawMessage) {

        log.info("message received : {}", rawMessage);
        notificationHandlerContext.handle(NotificationType.UNREAD_USERS,rawMessage);
        log.info("알림 전송 완료");
    }
}
