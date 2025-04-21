package com.sparta.moim.gathering.gathering.infrastructure.event.publisher;

import com.sparta.moim.gathering.gathering.application.dto.event.redis.GatheringJoinAdminEvent;
import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberPublisherRedis implements MemberPublisher {
  private final RedisTemplate<String, GatheringJoinAdminEvent> redisTemplate;

  @Override
  public void add(UUID gatheringId, String memberName) {
    GatheringJoinAdminEvent event = new GatheringJoinAdminEvent(gatheringId, memberName, MemberType.ADMIN);
    redisTemplate.opsForStream().add("stream:gathering_join", event.toMap());
  }

  // 미구현
  @Override
  public void revoke(UUID id, String owner) {
    // TODO: 멤버 탈퇴/강제 제거 기능 구현 예정
    log.warn("멤버 탈퇴 기능이 아직 구현되지 않았습니다. gatheringId: {}, owner: {}", id, owner);
  }
}
