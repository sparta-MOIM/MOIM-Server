package com.sparta.moim.organization.domain.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.domain.entity.Organization;
import java.util.List;
import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);
    Optional<Organization> findByTrackingId(String organizationTrackingId);
    Pagination<Organization> findAll(int page, int size);
    boolean existsByTrackingId(String organizationTrackingId);

    void saveAll(List<Organization> organizations);
}
