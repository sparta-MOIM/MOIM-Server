package com.moim.post.infrastructure.persistence.repository;

import com.moim.post.domain.repository.VoterRepository;
import com.moim.post.domain.vote.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVoterRepository extends JpaRepository<Voter, Long>, VoterRepository {
}
