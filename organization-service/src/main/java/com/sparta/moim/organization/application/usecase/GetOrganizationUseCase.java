package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import com.sparta.moim.organization.presentation.dto.GetOrganizationSummaryResponse;

public interface GetOrganizationUseCase {

    GetOrganizationResponse execute(String organizationId);

    Pagination<GetOrganizationSummaryResponse> execute(int page, int size);;
}
