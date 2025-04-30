package com.moim.post.infrastructure.kafka.adaptor;

import com.moim.post.application.event.OutBoxEventProcessor;
import com.moim.post.infrastructure.kafka.event.FeedEvent;
import com.moim.post.infrastructure.kafka.event.VoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventListener {

  private final OutBoxEventProcessor processor;

  @KafkaListener(topics = "feed-creating", groupId = "feed-group", containerFactory = "feedEventContainerFactory")
  public void consume(FeedEvent event) {
    log.info("FeedEvent Consume 완료: {}", event.getEventId());
    processor.feedCreatingEventProcess(event);
    log.info("읽기 DB에 Feed 저장 완료");
  }

  @KafkaListener(topics = "vote-creating", groupId = "vote-group", containerFactory = "voteEventContainerFactory")
  public void consume(VoteEvent event) {
    log.info("VoteEvent Consume 완료: {}", event.getEventId());
    processor.voteCreatingEventProcess(event);
    log.info("읽기 DB에 Vote 저장 완료");
  }

}
