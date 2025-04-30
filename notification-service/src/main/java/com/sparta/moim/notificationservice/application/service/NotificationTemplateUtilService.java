package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.application.dto.command.UnReadPostNotificationCommand;
import com.sparta.moim.notificationservice.application.exception.CannotFindNotificationTemplate;
import com.sparta.moim.notificationservice.domain.entity.NotificationTemplate;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import com.sparta.moim.notificationservice.domain.repository.NotificationTemplateRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationTemplateUtilService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    public String getNotificationContent(ApplyOrganizationNotificationCommand command) {
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findByNotificationType(NotificationType.ORGANIZATION_MOIM_REQUEST)
                .orElseThrow(CannotFindNotificationTemplate::new);

        return resolveTemplate(notificationTemplate.getContent(), command.toPlaceholderMap());
    }

    public String getNotificationContent(UnReadPostNotificationCommand command) {
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findByNotificationType(NotificationType.UNREAD_USERS)
                .orElseThrow(CannotFindNotificationTemplate::new);

        return resolveTemplate(notificationTemplate.getContent(), command.toPlaceholderMap());
    }

    private String resolveTemplate(String template, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}
