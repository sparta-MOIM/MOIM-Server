package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.exception.CannotFindNotificationType;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandlerContextImpl implements NotificationHandlerContext{

    private final Map<NotificationType, NotificationHandlerStrategy> notificationStrategyMap;

    public NotificationHandlerContextImpl(List<NotificationHandlerStrategy> notificationHandlerStrategyList) {
        this.notificationStrategyMap = notificationHandlerStrategyList.stream()
                .collect(Collectors.toMap(NotificationHandlerStrategy::getType, strategy -> strategy));
    }

    @Override
    public void handle(NotificationType type, String rawJson) {
        NotificationHandlerStrategy notificationHandlerStrategy = notificationStrategyMap.get(type);
        if (notificationHandlerStrategy == null) {
            throw new CannotFindNotificationType();
        }

        notificationHandlerStrategy.handleNotification(rawJson);
    }

}
