package com.moim.post.application.event;

import com.moim.post.domain.feed.view.FeedView;
import com.moim.post.domain.vote.view.VoteView;
import com.moim.post.infrastructure.kafka.event.FeedEvent;
import com.moim.post.infrastructure.kafka.event.VoteEvent;
import com.moim.post.infrastructure.persistence.repository.mongo.FeedViewRepository;
import com.moim.post.infrastructure.persistence.repository.mongo.ProcessedMessage;
import com.moim.post.infrastructure.persistence.repository.mongo.ProcessedMessageRepository;
import com.moim.post.infrastructure.persistence.repository.mongo.VoteViewRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OutBoxEventProcessingService implements OutBoxEventProcessor {

  private final FeedViewRepository feedViewRepository;
  private final VoteViewRepository voteViewRepository;
  private final ProcessedMessageRepository processedMessageRepository;

  public void feedCreatingEventProcess(FeedEvent event){
    UUID eventId = event.getEventId();
    if(processedMessageRepository.findByEventId(eventId).isEmpty()){
      feedViewRepository.save(FeedView.toView(event));
      processedMessageRepository.save(ProcessedMessage.create(eventId));
    }
  }

  public void voteCreatingEventProcess(VoteEvent event){
    UUID eventId = event.getEventId();
    if(processedMessageRepository.findByEventId(eventId).isEmpty()){
      voteViewRepository.save(VoteView.toView(event));
      processedMessageRepository.save(ProcessedMessage.create(eventId));
    }
  }

}
