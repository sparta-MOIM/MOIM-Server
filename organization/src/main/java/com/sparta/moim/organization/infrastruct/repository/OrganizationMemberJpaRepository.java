package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMemberJpaRepository extends JpaRepository<OrganizationMember, Long> {
    Optional<OrganizationMember> findByUserTrackingIdAndOrganization_TrackingId(UUID userTrackingId, UUID trackingId);

    List<OrganizationMember> findAllByOrganization(Organization organization);
}
