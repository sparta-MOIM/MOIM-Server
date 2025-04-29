package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.application.dto.command.ApplyOrganizationCommand;
import com.sparta.moim.organization.presentation.dto.ApplyOrganizationRequest;

public interface ApplyOrganizationUseCase {
    void execute(String organizationTrackingId, String userTrackingId,String username, ApplyOrganizationCommand applyOrganizationCommand);
}
