package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Optional<Organization> findByTrackingId(String organizationTrackingId) {
        return jpaRepository.findByTrackingId(UUID.fromString(organizationTrackingId));
    }

    @Override
    public Pagination<Organization> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Organization> organizationPage = jpaRepository.findAll(pageable);
        return Pagination.of(
                organizationPage.getNumber(),
                organizationPage.getSize(),
                organizationPage.getTotalElements(),
                organizationPage.getContent());
    }

    @Override
    public boolean existsByTrackingId(String organizationTrackingId) {
        return jpaRepository.existsByTrackingId(UUID.fromString(organizationTrackingId));
    }

    @Override
    public void saveAll(List<Organization> organizations) {
        jpaRepository.saveAll(organizations);
    }
}
