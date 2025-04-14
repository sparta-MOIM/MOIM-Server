package com.sparata.moim.gathering.gathering.infrastructure.repository;

import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import com.sparata.moim.gathering.gathering.domain.repository.GatheringRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGatheringRepository extends JpaRepository<Gathering, Long>, GatheringRepository {

}
