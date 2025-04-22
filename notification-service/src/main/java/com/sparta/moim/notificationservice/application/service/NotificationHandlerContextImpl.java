package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.exception.CannotFindNotificationType;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandlerContextImpl implements NotificationHandlerContext{

    private final Map<NotificationType, NotificationStrategy> notificationStrategyMap;

    public NotificationHandlerContextImpl(List<NotificationStrategy> notificationStrategyList) {
        this.notificationStrategyMap = notificationStrategyList.stream()
                .collect(Collectors.toMap(NotificationStrategy::getType, strategy -> strategy));
    }

    @Override
    public void handle(NotificationType type, String rawJson) {
        NotificationStrategy notificationStrategy = notificationStrategyMap.get(type);
        if (notificationStrategy == null) {
            throw new CannotFindNotificationType();
        }

        notificationStrategy.handleNotification(rawJson);
    }

}
