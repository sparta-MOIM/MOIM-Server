package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.domain.repository.EmitterRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String emitterId, Object event) {
        eventCache.put(emitterId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithMyMemberId(String memberId) {
        return eventCache.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmitterStartWithId(String memberId) {
        List<String> keysToDelete = emitters.keySet().stream()
                .filter(key -> key.startsWith(memberId))
                .toList();

        for (String key : keysToDelete) {
            emitters.remove(key);
        }
    }

    @Override
    public void deleteAllEventCacheStartWithId(String memberId) {
        List<String> keysToDelete = eventCache.keySet().stream()
                .filter(key -> key.startsWith(memberId))
                .toList();

        for (String key : keysToDelete) {
            eventCache.remove(key);
        }
    }
}
