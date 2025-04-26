package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;

public interface CreateOrganizationUseCase {
    void execute(String userTrackingId,CreateOrganizationCommand command);
}
