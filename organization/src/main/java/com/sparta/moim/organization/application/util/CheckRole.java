package com.sparta.moim.organization.application.util;

import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.exception.OrganizationMasterRequiredException;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckRole {

    private final OrganizationMemberRepository organizationMemberRepository;

    public void checkMaster(String userTrackingId, String organizationTrackingId) {
        OrganizationMember member = organizationMemberRepository
                .findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationMember::new);

        if (!OrganizationMemberRole.MASTER.equals(member.getRole())) {
            throw new OrganizationMasterRequiredException();
        }
    }

}