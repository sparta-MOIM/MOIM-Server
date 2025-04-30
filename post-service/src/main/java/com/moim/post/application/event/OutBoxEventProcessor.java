package com.moim.post.application.event;

import com.moim.post.infrastructure.kafka.event.FeedEvent;
import com.moim.post.infrastructure.kafka.event.VoteEvent;

public interface OutBoxEventProcessor {
  void feedCreatingEventProcess(FeedEvent event);
  void voteCreatingEventProcess(VoteEvent event);
}
