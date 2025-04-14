package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import org.springframework.stereotype.Service;

@Service
public class ApplyOrganizationNotificationServiceImpl implements ApplyOrganizationNotificationService {
    @Override
    public void sendApplyOrganizationNotification(ApplyOrganizationNotificationCommand command) {
        // todo - 알림 보내고
        // 1. fcm 토큰 조회
        // 2. fcm 토큰으로 알림 보내기
        // todo - 알림 저장하고
        // 1. 알림 저장소에 알림 저장하기
    }
}
