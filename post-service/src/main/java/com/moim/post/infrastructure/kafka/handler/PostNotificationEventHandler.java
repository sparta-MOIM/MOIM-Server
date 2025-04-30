package com.moim.post.infrastructure.kafka.handler;

import com.moim.post.infrastructure.kafka.event.PostNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostNotificationEventHandler implements EventHandler<PostNotificationEvent> {
  private final KafkaTemplate<String, PostNotificationEvent> kafkaTemplate;

  @Override
  public void send(String topic, PostNotificationEvent event) {
    log.info("PostNotificationEvent = {}", event.toString());
    kafkaTemplate.send(topic, event);
  }
}
