package com.moim.post.infrastructure.persistence.repository;

import com.moim.post.domain.repository.command.VoteCommandRepository;
import com.moim.post.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVoteRepository extends JpaRepository<Vote, Long>, VoteCommandRepository {
}
