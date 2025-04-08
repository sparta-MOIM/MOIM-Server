package com.sparta.moim.organization.presentation.controller;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.CreateOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.GetOrganizationUseCase;
import com.sparta.moim.organization.presentation.dto.CreateOrganizationRequest;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final CreateOrganizationUseCase createOrganizationUseCase;
    private final GetOrganizationUseCase getOrganizationUseCase;
    private final CommandMapper commandMapper;

    @PostMapping
    public ResponseEntity<ApiResponseData<String>> createOrganization(@RequestBody @Valid CreateOrganizationRequest request) {
        createOrganizationUseCase.execute(commandMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @GetMapping("/{organizationTrackingId}")
    public ResponseEntity<ApiResponseData<GetOrganizationResponse>> getOrganization(@PathVariable String organizationTrackingId) {
        return ResponseEntity.ok(ApiResponseData.success(getOrganizationUseCase.execute(organizationTrackingId)));
    }
}
