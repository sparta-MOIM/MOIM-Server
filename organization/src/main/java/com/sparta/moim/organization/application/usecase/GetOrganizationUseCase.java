package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;

public interface GetOrganizationUseCase {

    GetOrganizationResponse execute(String organizationId);
}
