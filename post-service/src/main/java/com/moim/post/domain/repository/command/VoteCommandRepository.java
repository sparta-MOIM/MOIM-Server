package com.moim.post.domain.repository.command;

import com.moim.post.domain.vote.Vote;
import java.util.Optional;
import java.util.UUID;

public interface VoteCommandRepository {
  Vote save(Vote vote);
  Optional<Vote> findByTrackingId(UUID id);
}
