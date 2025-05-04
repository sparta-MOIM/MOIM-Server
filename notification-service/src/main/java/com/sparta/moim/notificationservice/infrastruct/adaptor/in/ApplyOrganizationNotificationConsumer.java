package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.sparta.moim.notificationservice.application.service.NotificationHandlerContext;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationConsumer implements NotificationConsumer{

    private final NotificationHandlerContext notificationHandlerContext;
    private final MeterRegistry meterRegistry;
    @Override
    @KafkaListener(topics = "organization-apply-notification", groupId = "notification-group", containerFactory = "stringContainerFactory")
    public void consume(String rawMessage) {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("message received : {}", rawMessage);
        notificationHandlerContext.handle(NotificationType.ORGANIZATION_MOIM_REQUEST,rawMessage);
        log.info("알림 전송 완료");
        sample.stop(Timer.builder("notification.kafka.listener.processing")
                .description("Kafka Listener Processing Time")
                .tags("topic", "organization-apply-notification") // 동적으로 topic을 태그로
                .register(meterRegistry));
    }
}
