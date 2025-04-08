package com.sparta.moim.organization.presentation.controller;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.CreateOrganizationUseCase;
import com.sparta.moim.organization.presentation.dto.CreateOrganizationRequest;
import com.sparta.moim.organization.presentation.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final CreateOrganizationUseCase createOrganizationUseCase;
    private final DtoMapper dtoMapper;

    @PostMapping
    public ResponseEntity<ApiResponseData<String>> createOrganization(
        CreateOrganizationRequest request) {
        createOrganizationUseCase.execute(dtoMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponseData.success(null, "모임 생성이 완료되었습니다."));
    }
}
