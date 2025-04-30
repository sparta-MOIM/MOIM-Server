package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
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
    public Pagination<OrganizationMember> findAllByOrganization(Organization organization, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrganizationMember> organizationMembers = jpaRepository.findAllByOrganization(organization, pageable);
        return Pagination.of(
                organizationMembers.getNumber(),
                organizationMembers.getSize(),
                organizationMembers.getTotalElements(),
                organizationMembers.getContent()
        );
    }

    @Override
    public void saveAll(List<OrganizationMember> members) {
        jpaRepository.saveAll(members);
    }

    @Override
    public Optional<OrganizationMember> findByOrganizationAndNickname(Organization organization, String nickaname) {
        return jpaRepository.findByOrganizationAndNickname(organization, nickaname);
    }

    @Override
    public List<OrganizationMember> findAllByOrganizationAndRoleIn(Organization organization,
                                                                   List<OrganizationMemberRole> organizationMemberRoles) {
        return jpaRepository.findAllByOrganizationAndRoleIn(organization, organizationMemberRoles );
    }

    @Override
    public Optional<OrganizationMember> findByMemberTrackingId(String memberTrackingId) {
        return jpaRepository.findByTrackingId(UUID.fromString(memberTrackingId));
    }


}
