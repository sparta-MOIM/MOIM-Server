package com.sparta.moim.notificationservice.presentation.controller;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.notificationservice.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        return notificationService.subscribe("68926367-c01f-4f88-8f10-4c9797b77f8e", lastEventId); // todo - userTrackingId를 실제 값으로 변경
    }

    @GetMapping(value = "/test")
    public ResponseEntity<ApiResponseData<String>> test() {
        notificationService.sendNotificationToMember("68926367-c01f-4f88-8f10-4c9797b77f8e", "테스트 알림");
        return ResponseEntity.ok(ApiResponseData.success("test"));
    }

 
}
