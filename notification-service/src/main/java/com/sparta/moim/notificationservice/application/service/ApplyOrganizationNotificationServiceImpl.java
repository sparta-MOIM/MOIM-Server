package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationServiceImpl implements ApplyOrganizationNotificationService {

    private final NotificationTemplateUtilService notificationTemplateUtilService;
    private final NotificationService notificationService;

    @Override
    public void sendApplyOrganizationNotification(ApplyOrganizationNotificationCommand command) {
        // 템플릿을 가져와서 내용과 결합한다.
        String content = notificationTemplateUtilService.geteNotificationContent(command);

        // 알림을 보낸다.
        notificationService.sendNotificationToMembers(command.getReceiverTrackingIds(), content);
        log.info("알림을 보냈습니다. 내용: {}", content);
        // todo - 알림 보내고
        // todo - 알림 저장하고
    }
}
