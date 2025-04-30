package com.moim.post.infrastructure.kafka.handler;

public interface EventHandler<T> {
  void send(String topic, T event);
}
