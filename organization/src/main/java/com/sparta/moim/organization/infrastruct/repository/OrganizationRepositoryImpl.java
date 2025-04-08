package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final OrganizationJpaRepository jpaRepository;

    @Override
    public Organization save(Organization organization) {
        return jpaRepository.save(organization);
    }

    @Override
    public Optional<Organization> findByTrackingId(UUID organizationUUID) {
        return jpaRepository.findByTrackingId(organizationUUID);
    }
}
