package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationMemberRepositoryImpl implements OrganizationMemberRepository {

    private final OrganizationMemberJpaRepository jpaRepository;
    @Override
    public void save(OrganizationMember organizationMember) {
        jpaRepository.save(organizationMember);
    }
}
