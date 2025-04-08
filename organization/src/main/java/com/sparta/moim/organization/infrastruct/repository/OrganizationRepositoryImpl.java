package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {
    private final OrganizationJpaRepository jpaRepository;

    @Override
    public void save(Organization organization) {
        jpaRepository.save(organization);
    }
}
