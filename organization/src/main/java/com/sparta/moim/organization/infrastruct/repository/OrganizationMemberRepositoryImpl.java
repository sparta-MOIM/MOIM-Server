package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @Override
    public Optional<OrganizationMember> findByUserTrackingIdAndOrganizationTrackingId(String userTrackingId,
                                                                                      String trackingId) {
        return jpaRepository.findByUserTrackingIdAndOrganization_TrackingId(UUID.fromString(userTrackingId), UUID.fromString(trackingId));
    }

    @Override
    public List<OrganizationMember> findAllByOrganization(Organization organization) {
        return jpaRepository.findAllByOrganization(organization);
    }

    @Override
    public void saveAll(List<OrganizationMember> members) {
        jpaRepository.saveAll(members);
    }
}
