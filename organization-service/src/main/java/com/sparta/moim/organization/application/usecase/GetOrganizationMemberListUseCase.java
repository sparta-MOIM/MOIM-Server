package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.presentation.dto.GetOrganizationMemberResponse;

public interface GetOrganizationMemberListUseCase {

    Pagination<GetOrganizationMemberResponse> execute(String organizationTrackingId, int page, int size);
}
