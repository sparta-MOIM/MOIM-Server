package com.moim.post.infrastructure.kafka.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moim.post.infrastructure.kafka.TopicUtil;
import com.moim.post.infrastructure.kafka.event.FeedEvent;
import com.moim.post.infrastructure.kafka.event.VoteEvent;
import com.moim.post.infrastructure.persistence.outbox.FeedPayload;
import com.moim.post.infrastructure.persistence.outbox.OutBoxEventType;
import com.moim.post.infrastructure.persistence.outbox.OutboxEvent;
import com.moim.post.infrastructure.persistence.outbox.VotePayload;
import com.moim.post.infrastructure.persistence.repository.jpa.JpaOutBoxEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OutboxEventDispatcher {

  private final KafkaTemplate<String, FeedEvent> kafkaFeedEventTemplate;
  private final KafkaTemplate<String, VoteEvent> kafkaVoteEventTemplate;
  private final JpaOutBoxEventRepository outboxEventRepository;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedDelay = 5000)
  public void dispatch() {
    List<OutboxEvent> outboxEvents = outboxEventRepository.findUnprocessedEvents();
    for (OutboxEvent outboxEvent : outboxEvents) {
      try {
        if(outboxEvent.getType() == OutBoxEventType.CREATE_FEED){
          FeedPayload payload = objectMapper.readValue(outboxEvent.getPayload(), FeedPayload.class);
          FeedEvent feedEvent = FeedEvent.toEvent(outboxEvent.getId(),payload);
          kafkaFeedEventTemplate.executeInTransaction(operations -> {
            operations.send(TopicUtil.FEED_CREATING,feedEvent);
            outboxEvent.markAsProcessed();
            return true;
          });
        }

        if(outboxEvent.getType() == OutBoxEventType.CREATE_VOTE){
          VotePayload payload = objectMapper.readValue(outboxEvent.getPayload(), VotePayload.class);
          VoteEvent voteEvent = VoteEvent.toEvent(outboxEvent.getId(), payload);
          kafkaVoteEventTemplate.executeInTransaction(operations -> {
            operations.send(TopicUtil.VOTE_CREATING,voteEvent);
            outboxEvent.markAsProcessed();
            return true;
          });
        }

      } catch (JsonProcessingException e) {
        //todo: 예외 처리
        log.error("Failed to serialize feed", e);
      }
    }
  }
}