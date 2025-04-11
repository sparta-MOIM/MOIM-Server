package com.sparta.moim.organization.domain.repository;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationMemberRepository {

    void save(OrganizationMember organizationMember);

    Optional<OrganizationMember> findByUserTrackingIdAndOrganizationTrackingId(String userTrackingId, String trackingId);

    List<OrganizationMember> findAllByOrganization(Organization organization);

    void saveAll(List<OrganizationMember> members);

    Optional<OrganizationMember> findByOrganizationAndNickname(Organization organization, String nickaname);
}
