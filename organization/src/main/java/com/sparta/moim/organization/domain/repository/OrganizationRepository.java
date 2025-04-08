package com.sparta.moim.organization.domain.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {
    Organization save(Organization organization);
    Optional<Organization> findByTrackingId(UUID organizationUUID);
}
