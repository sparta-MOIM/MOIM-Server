package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.util.List;

public interface OrganizationCheckRoleUseCase {
    boolean execute(String organizationTrackingId, String userTrackingId, List<OrganizationMemberRole> roles);
}
