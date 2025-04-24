package com.sparta.moim.organization.application.dto.command;

import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeOrganizationRoleCommand {
    private OrganizationMemberRole role;
    private String memberTrackingId;
}
