package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.exception.OrganizationMasterRequiredException;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckRoleService {

    private final OrganizationMemberRepository organizationMemberRepository;

    public void checkMaster(String userTrackingId, String organizationTrackingId) {
        OrganizationMember member = organizationMemberRepository
                .findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationMember::new);

        if (!OrganizationMemberRole.MASTER.equals(member.getRole())) {
            throw new OrganizationMasterRequiredException();
        }
    }

    public boolean checkRole(String userTrackingId, String organizationTrackingId, List<OrganizationMemberRole> roles){
        OrganizationMember member = organizationMemberRepository
                .findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId).orElse(null);

        if(member == null){
            return false;
        }

        if(!roles.contains(member.getRole())){
            return false;
        }

        return true;
    }

}