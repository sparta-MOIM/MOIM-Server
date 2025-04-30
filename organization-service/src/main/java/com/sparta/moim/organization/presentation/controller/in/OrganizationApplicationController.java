package com.sparta.moim.organization.presentation.controller.in;


import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.organization.application.usecase.AcceptOrganizationApplicationUseCase;
import com.sparta.moim.organization.application.usecase.ApplyOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.RejectOrganizationApplicationUseCase;
import com.sparta.moim.organization.presentation.dto.ApplyOrganizationRequest;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations/{organizationTrackingId}/applications")
@RequiredArgsConstructor
public class OrganizationApplicationController {

    private final ApplyOrganizationUseCase applyOrganizationUseCase;
    private final AcceptOrganizationApplicationUseCase acceptOrganizationApplicationUseCase;
    private final RejectOrganizationApplicationUseCase rejectOrganizationApplicationUseCase;
    private final CommandMapper commandMapper;

    @PostMapping
    public ResponseEntity<ApiResponseData<String>> applyOrganization(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String organizationTrackingId, @RequestBody @Valid ApplyOrganizationRequest applyOrganizationRequest){
        applyOrganizationUseCase.execute(organizationTrackingId, customUserDetails.getTrackingId().toString(),  customUserDetails.getUsername(),commandMapper.toCommand(applyOrganizationRequest));
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @PostMapping("/{applicationTrackingId}/accept") //todo- Application 이름 변경
    public ResponseEntity<ApiResponseData<String>> acceptApplication(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable String organizationTrackingId, @PathVariable String applicationTrackingId){
        acceptOrganizationApplicationUseCase.execute(organizationTrackingId,applicationTrackingId, customUserDetails.getTrackingId().toString());
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @DeleteMapping("/{applicationTrackingId}/reject")
    public ResponseEntity<ApiResponseData<String>> rejectApplication(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable String organizationTrackingId, @PathVariable String applicationTrackingId){
        rejectOrganizationApplicationUseCase.execute(organizationTrackingId,applicationTrackingId, customUserDetails.getTrackingId().toString());
        return ResponseEntity.ok(ApiResponseData.success(null));
    }
}