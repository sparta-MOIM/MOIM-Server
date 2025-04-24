package com.sparta.moim.organization.presentation.controller.in;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.organization.application.usecase.ChangeAuthorityUseCase;
import com.sparta.moim.organization.presentation.dto.ChangeOrganizationRoleRequest;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations/{organizationTrackingId}")
@RequiredArgsConstructor
public class OrganizationManageController {

    private final ChangeAuthorityUseCase changeAuthorityUseCase;
    private final CommandMapper commandMapper;
    // 멤버 권한 변경
    @PostMapping("/members/{userTrackingId}/role")
    public ResponseEntity<ApiResponseData<Void>> changeRole(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String organizationTrackingId,
            @PathVariable String targetMemberTrackingId,
            @RequestBody @Valid ChangeOrganizationRoleRequest changeOrganizationRoleRequest) {
        changeAuthorityUseCase.execute(
                customUserDetails.getTrackingId().toString(),
                organizationTrackingId,
                targetMemberTrackingId,
                commandMapper.toCommand(changeOrganizationRoleRequest));
        return ResponseEntity.ok(ApiResponseData.success(null));
    }
}

