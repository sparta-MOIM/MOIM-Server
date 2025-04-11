package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationApplicationRepositoryImpl implements OrganizationApplicationRepository {
    private final OrganizationApplicationJpaRepository jpaRepository;

    @Override
    public void save(OrganizationApplication application) {
        jpaRepository.save(application);
    }

    public Optional<OrganizationApplication> findByApplicationTrackingIdAndOrganizationTrackingId(String applicationTrackingId, String organizationTrackingId) {
        return jpaRepository.findByTrackingIdAndOrganizationTrackingId(UUID.fromString(applicationTrackingId),UUID.fromString(organizationTrackingId));
    }
}
