package com.moim.post.infrastructure.persistence.repository.jpa;

import com.moim.post.domain.repository.command.VoterCommandRepository;
import com.moim.post.domain.vote.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVoterRepository extends JpaRepository<Voter, Long>, VoterCommandRepository {
}
