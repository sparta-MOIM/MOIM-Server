package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMemberJpaRepository extends JpaRepository<OrganizationMember, Long> {
    Optional<OrganizationMember> findByUserTrackingIdAndOrganization_TrackingId(UUID userTrackingId, UUID trackingId);

    List<OrganizationMember> findAllByOrganization(Organization organization);

    Page<OrganizationMember> findAllByOrganization(Organization organization, Pageable pageable);

    Optional<OrganizationMember> findByOrganizationAndNickname(Organization organization, String nickname);

    List<OrganizationMember> findAllByOrganizationAndRoleIn(Organization organization, Collection<OrganizationMemberRole> roles);

    Optional<OrganizationMember> findByTrackingId(UUID trackingId);
}
