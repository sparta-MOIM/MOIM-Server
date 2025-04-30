package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

public interface NotificationConsumer {
    void consume(String rawMessage);
}
