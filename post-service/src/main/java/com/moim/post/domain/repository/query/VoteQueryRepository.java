package com.moim.post.domain.repository.query;

import com.moim.post.domain.vote.Vote;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoteQueryRepository {
  Optional<Vote> findVote(UUID id);

  Page<Vote> searchVote(
      Optional<UUID> organizationId,
      Optional<String> word,
      Optional<LocalDateTime> start,
      Optional<LocalDateTime> end,
      Pageable pageable
  );

}
