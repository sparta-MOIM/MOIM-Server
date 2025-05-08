package com.sparta.moim.notificationservice.infrastruct.util;

import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationMockDataInitializer implements ApplicationRunner {

    private final NotificationDataCreateService dataCreateService;
    private final NotificationRepository notificationRepository;
    @Override
    public void run(ApplicationArguments args) {
        if(notificationRepository.findById(1L).isPresent()) {
            log.info("알림 데이터가 이미 존재합니다.");
            return;
        }
        log.info("🚀 알림 목데이터 생성 시작");

        long start = System.currentTimeMillis();

        for (int day = 1; day <= 31; day++) {
            dataCreateService.generateDailyNotifications(day);
        }

        long total = System.currentTimeMillis() - start;
        log.info("🎉 알림 목데이터 전체 생성 완료 (총 소요 시간: {}ms)", total);
    }
}
