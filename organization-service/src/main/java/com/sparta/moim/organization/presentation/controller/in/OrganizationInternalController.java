package com.sparta.moim.organization.presentation.controller.in;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.CheckOrganizationExistsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/organizations")
@RequiredArgsConstructor
public class OrganizationInternalController {

    private final CheckOrganizationExistsUseCase checkOrganizationExistsUseCase;

    @RequestMapping("/{organizationId}/exists")
    public ApiResponseData<Boolean> checkOrganizationExists( @PathVariable("organizationId") String organizationId
    ) {
        return ApiResponseData.success(checkOrganizationExistsUseCase.execute(organizationId));
    }

}
