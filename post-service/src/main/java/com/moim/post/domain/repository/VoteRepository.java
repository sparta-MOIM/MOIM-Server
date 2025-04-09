package com.moim.post.domain.repository;

import com.moim.post.domain.vote.Vote;

public interface VoteRepository {
  Vote save(Vote vote);
}
