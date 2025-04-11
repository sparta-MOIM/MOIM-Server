package com.moim.post.domain.vote;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteStatus {
  APPROVE("찬성"),
  DISAPPROVE("반대");

  private final String viewStatus;

}
