package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.application.dto.command.ChangeOrganizationRoleCommand;

public interface ChangeAuthorityUseCase {
    void execute(String organizationTrackingId, String targetMemberTrackingId, String userTrackingId, ChangeOrganizationRoleCommand command);
}
