package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
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
}
