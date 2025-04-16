package com.moim.post.application.event.kafka.handler;

import com.moim.post.application.event.PostNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostNotificationEventHandler {
  private final KafkaTemplate<String, PostNotificationEvent> kafkaTemplate;

  public void send(String topic, PostNotificationEvent event) {
    log.info("PostNotificationEvent = {}", event.toString());
    kafkaTemplate.send(topic, event);
  }
}
