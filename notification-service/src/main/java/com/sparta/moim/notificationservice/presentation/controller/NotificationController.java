package com.sparta.moim.notificationservice.presentation.controller;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.notificationservice.application.service.NotificationService;
import com.sparta.moim.notificationservice.application.usecase.GetNotificationsUseCase;
import com.sparta.moim.notificationservice.application.usecase.ReadNotificationUseCase;
import com.sparta.moim.notificationservice.presentation.dto.GetNotificationResponse;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final GetNotificationsUseCase getNotificationsUseCase;
    private final ReadNotificationUseCase readNotificationUseCase;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ){
        return notificationService.subscribe(customUserDetails.getTrackingId().toString(), lastEventId);
    }

    @GetMapping
    public ResponseEntity<ApiResponseData<Pagination<GetNotificationResponse>>> getNotifications(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(value = "isRead", required = false) Boolean isRead,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "페이지 번호 0 이상이어야 합니다.") int size
    ) {
        Pagination<GetNotificationResponse> response = getNotificationsUseCase.execute(
                customUserDetails.getTrackingId().toString(), isRead, page, size);
        return ResponseEntity.ok(ApiResponseData.success(response));
    }

    @PatchMapping("/{notificationTrackingId}/read")
    public ResponseEntity<ApiResponseData<String>> readNotification(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable(name = "notificationTrackingId") String notificationTrackingId) {
        readNotificationUseCase.execute(customUserDetails.getTrackingId().toString(), notificationTrackingId);
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<ApiResponseData<String>> test(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        notificationService.sendNotificationToMember(customUserDetails.getTrackingId().toString(), "테스트 알림");
        return ResponseEntity.ok(ApiResponseData.success("test"));
    }

}
