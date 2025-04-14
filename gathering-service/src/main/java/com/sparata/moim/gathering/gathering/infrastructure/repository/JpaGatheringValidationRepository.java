package com.sparata.moim.gathering.gathering.infrastructure.repository;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import com.sparata.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGatheringValidationRepository extends JpaRepository<Gathering,Long>, GatheringValidationRepository {
}
