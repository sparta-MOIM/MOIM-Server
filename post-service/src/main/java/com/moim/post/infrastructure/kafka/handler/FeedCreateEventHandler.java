package com.moim.post.infrastructure.kafka.handler;

import com.moim.post.infrastructure.kafka.event.FeedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedCreateEventHandler implements EventHandler<FeedEvent> {
  private final KafkaTemplate<String, FeedEvent> kafkaTemplate;

  @Override
  public void send(String topic, FeedEvent event) {
    log.info("FeedCreateEvent = {}", event.toString());
    kafkaTemplate.send(topic, event);
  }
}
