package com.moim.post.infrastructure.persistence.outbox;

public enum OutBoxEventType {
  CREATE_FEED,
  UPDATE_FEED,
  DELETE_FEED,
  CREATE_VOTE,
  UPDATE_VOTE,
  DELETE_VOTE
}
