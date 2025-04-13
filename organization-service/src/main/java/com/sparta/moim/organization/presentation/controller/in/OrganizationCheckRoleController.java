package com.sparta.moim.organization.presentation.controller.in;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.OrganizationCheckRoleUseCase;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationCheckRoleController {

    private final OrganizationCheckRoleUseCase organizationCheckRoleUseCase;

    @GetMapping("/{organizationTrackingId}/members/{userTrackingId}/has-role")
    public ResponseEntity<ApiResponseData<Boolean>> checkRole(
            @PathVariable String organizationTrackingId,
            @PathVariable String userTrackingId,
            @RequestParam List<OrganizationMemberRole> roles){
        return ResponseEntity.ok(ApiResponseData.success(organizationCheckRoleUseCase.execute(organizationTrackingId,userTrackingId, roles)));
    }
}
