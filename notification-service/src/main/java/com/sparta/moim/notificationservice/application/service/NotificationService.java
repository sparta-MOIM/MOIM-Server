package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.domain.repository.EmitterRepository;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    //연결 지속 시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L; // 1시간

    public SseEmitter subscribe(String memberTrackingId, String lastEventId){
        // 고유한 아이디 생성
        String emitterId = memberTrackingId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 시간 초과나 비동기 요청 실패시 자동으로 삭제
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));

        // 최초 연결시 더미데이터가 없으면 503 오류 발생. 더미 데이터 생성
        sendToClient(emitter, emitterId, "EventStrea Createed. [memberId=" + memberTrackingId + "]");

        // lastEventId가 있다는것은 연결이 종료됐었다는 의미. 이벤트가 남아 있을 경우 클라이언트에게 전송
        if(!lastEventId.isEmpty()){
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithMyMemberId(memberTrackingId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey())<0)
                    .forEach(entry -> sendToClient(emitter,entry.getKey(),entry.getValue()));
        }
        return emitter;

    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object object) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(object));
        } catch (IOException exception){
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException("전송 실패");
        }
    }

}
