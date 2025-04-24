package com.sparta.moim.gathering.gathering.infrastructure.event.listener;

import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisStreamJoinListener implements StreamListener<String, MapRecord<String, String, String>> {
  private final MemberRepository memberRepository;

  @Override
  public void onMessage(MapRecord<String, String, String> message) {
    UUID gatheringId = UUID.fromString(message.getValue().get("gathering_id"));
    Member owner = memberRepository.existsOwner(gatheringId).orElse(null);
    // 리더가 존재하지 않는 경우 무시
    if (owner == null) {
      return;
    }

    memberRepository.save(Member.builder()
        .gatheringId(gatheringId)
        .memberId(UUID.fromString(message.getValue().get("member_name")))
        .type(MemberType.valueOf(message.getValue().get("type")))
        .joinTime(LocalDateTime.now())
        .build());
  }
}
