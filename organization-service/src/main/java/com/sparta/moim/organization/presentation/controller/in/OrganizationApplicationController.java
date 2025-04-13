package com.sparta.moim.organization.presentation.controller.in;


import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.AcceptOrganizationApplicationUseCase;
import com.sparta.moim.organization.application.usecase.ApplyOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.RejectOrganizationApplicationUseCase;
import com.sparta.moim.organization.presentation.dto.ApplyOrganizationRequest;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponseData<String>> applyOrganization(@PathVariable String organizationTrackingId, @RequestBody @Valid ApplyOrganizationRequest applyOrganizationRequest){
        applyOrganizationUseCase.execute(organizationTrackingId, "12345678-c01f-4f88-8f10-4c9797b772cf", commandMapper.toCommand(applyOrganizationRequest)); //todo - userTrackingId를 실제 값으로 변경
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @PostMapping("/{applicationTrackingId}/accept")
    public ResponseEntity<ApiResponseData<String>> acceptApplication(@PathVariable String organizationTrackingId, @PathVariable String applicationTrackingId){
        acceptOrganizationApplicationUseCase.execute(organizationTrackingId,applicationTrackingId, "68926367-c01f-4f88-8f10-4c9797b77f8e"); // todo - userTrackingId를 실제 값으로 변경
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @DeleteMapping("/{applicationTrackingId}/reject")
    public ResponseEntity<ApiResponseData<String>> rejectApplication(@PathVariable String organizationTrackingId, @PathVariable String applicationTrackingId){
        rejectOrganizationApplicationUseCase.execute(organizationTrackingId,applicationTrackingId, "68926367-c01f-4f88-8f10-4c9797b77f8e"); // todo - userTrackingId를 실제 값으로 변경
        return ResponseEntity.ok(ApiResponseData.success(null));
    }
}
