package com.sparta.moim.organization.presentation.controller;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.CreateOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.DeleteOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.GetOrganizationUseCase;
import com.sparta.moim.organization.application.usecase.UpdateOrganizationUseCase;
import com.sparta.moim.organization.presentation.dto.CreateOrganizationRequest;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import com.sparta.moim.organization.presentation.dto.GetOrganizationSummaryResponse;
import com.sparta.moim.organization.presentation.dto.UpdateOrganizationRequest;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final CreateOrganizationUseCase createOrganizationUseCase;
    private final GetOrganizationUseCase getOrganizationUseCase;
    private final DeleteOrganizationUseCase deleteORganizationUseCase;
    private final UpdateOrganizationUseCase updateOrganizationUseCase;
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

    @GetMapping
    public ResponseEntity<ApiResponseData<Pagination<GetOrganizationSummaryResponse>>> getOrganizationSummaryList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(ApiResponseData.success(getOrganizationUseCase.execute(page, size)));
    }

    @DeleteMapping("/{organizationTrackingId}")
    public ResponseEntity<ApiResponseData<String>> deleteOrganization(@PathVariable String organizationTrackingId) {
        deleteORganizationUseCase.execute("68926367-c01f-4f88-8f10-4c9797b77f8e",organizationTrackingId); //todo- userTrackingId는
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @PutMapping("{/organizationTrackingId}")
    public ResponseEntity<ApiResponseData<String>> updateOrganization(@PathVariable String organizationTrackingId, @RequestBody @Valid UpdateOrganizationRequest request) {
        updateOrganizationUseCase.execute(organizationTrackingId,"68926367-c01f-4f88-8f10-4c9797b77f8e",commandMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponseData.success(null));
    }
}
