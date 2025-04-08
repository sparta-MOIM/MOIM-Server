package com.sparta.moim.gathering.infrastruct.repository;

import com.sparta.moim.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.domain.repository.GatheringRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGatheringRepository extends JpaRepository<Gathering, UUID>, GatheringRepository {

}
