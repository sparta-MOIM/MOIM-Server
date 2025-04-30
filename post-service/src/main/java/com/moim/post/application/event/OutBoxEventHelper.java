package com.moim.post.application.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import com.moim.post.infrastructure.persistence.outbox.FeedPayload;
import com.moim.post.infrastructure.persistence.outbox.OutboxEvent;
import com.moim.post.infrastructure.persistence.outbox.VotePayload;
import com.moim.post.infrastructure.persistence.repository.jpa.JpaOutBoxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutBoxEventHelper {

  private final ObjectMapper objectMapper;
  private final JpaOutBoxEventRepository outboxEventRepository;

  public void publishEvent(String type, Feed feed){
    try{
      String payload = objectMapper.writeValueAsString(new FeedPayload(feed));
      OutboxEvent event = OutboxEvent.create(
          "Feed",
          feed.getTrackingId().toString(),
          type,
          payload
      );
      outboxEventRepository.save(event);
    }catch (JsonProcessingException e) {
      // todo: 예외 던지기
      log.error("JSON 변환시 예외 발생");
    }
  }

  public void publishEvent(String type, Vote vote){
    try{
      String payload = objectMapper.writeValueAsString(new VotePayload(vote));
      OutboxEvent event = OutboxEvent.create(
          "Vote",
          vote.getTrackingId().toString(),
          type,
          payload
      );
      outboxEventRepository.save(event);
    }catch (JsonProcessingException e) {
      // todo: 예외 던지기
      log.error("직렬화 예외 발생");
    }
  }
}
