package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationApplicationJpaRepository extends JpaRepository<OrganizationApplication, Long> {
    Optional<OrganizationApplication> findByTrackingIdAndOrganizationTrackingId(UUID trackingId, UUID organizationTrackingId);
}
