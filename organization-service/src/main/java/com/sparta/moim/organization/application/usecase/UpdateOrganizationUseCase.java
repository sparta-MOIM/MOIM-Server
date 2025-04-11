package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.application.dto.command.UpdateOrganizationCommand;

public interface UpdateOrganizationUseCase {
    void execute(String organizationTrackingId, String userTrackingId, UpdateOrganizationCommand updateOrganizationCommand);
}
