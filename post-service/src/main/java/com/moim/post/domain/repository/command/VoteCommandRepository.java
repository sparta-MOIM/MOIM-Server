package com.moim.post.domain.repository.command;

import com.moim.post.domain.vote.Vote;

public interface VoteCommandRepository {
  Vote save(Vote vote);
}
