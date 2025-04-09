package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.usecase.OrganizationCheckRoleUseCase;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckOrganizationRoleService implements OrganizationCheckRoleUseCase {

    private final CheckRoleService checkRoleService;

    @Override
    public boolean execute(String organizationTrackingId, String userTrackingId, List<OrganizationMemberRole> roles) {
        return checkRoleService.checkRole(userTrackingId, organizationTrackingId, roles);
    }
}
