package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationMemberService {

    public final OrganizationMemberRepository organizationMemberRepository;

    public List<OrganizationMember> getOrganizationMembers(Organization organization) {
        return organizationMemberRepository.findAllByOrganization(organization);
    }

    public List<OrganizationMember> getOrganizationManagers(Organization organization) {
        return organizationMemberRepository.findAllByOrganizationAndRoleIn(organization, List.of(OrganizationMemberRole.MANAGER, OrganizationMemberRole.MASTER));
    }

}
