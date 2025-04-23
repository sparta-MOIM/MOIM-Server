package com.sparta.moim.gathering.gathering.infrastructure.event.listener;

import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RedisStreamLeaveListener implements StreamListener<String, MapRecord<String, String, String>> {
  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public void onMessage(MapRecord<String, String, String> message) {
    UUID gatheringId = UUID.fromString(message.getValue().get("gathering_id"));
    String memberName = message.getValue().get("member_name");
    memberRepository.deleteByGatheringIdAndMemberId(gatheringId, memberName);
  }
}
