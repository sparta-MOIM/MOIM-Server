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
    @Override
    public void sendApplyOrganizationNotification(ApplyOrganizationNotificationCommand command) {
        String content = notificationTemplateUtilService.geteNotificationContent(command);

        log.info(content);

        // todo - 알림 보내고
        // 1. fcm 토큰 조회
        // 2. fcm 토큰으로 알림 보내기
        // todo - 알림 저장하고
        // 1. 알림 저장소에 알림 저장하기
    }
}
