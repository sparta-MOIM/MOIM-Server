package com.sparta.moim.organization.domain.repository;

import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import java.util.Optional;

public interface OrganizationApplicationRepository {
    void save(OrganizationApplication application);

    Optional<OrganizationApplication> findByApplicationTrackingIdAndOrganizationTrackingId(String applicationTrackingId, String organizationTrackingId);
}
