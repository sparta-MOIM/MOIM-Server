package com.sparta.moim.organization.presentation.controller.in;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.organization.application.usecase.GetOrganizationMemberListUseCase;
import com.sparta.moim.organization.application.usecase.UpdateMemberInfoUseCase;
import com.sparta.moim.organization.presentation.dto.GetOrganizationMemberResponse;
import com.sparta.moim.organization.presentation.dto.GetOrganizationSummaryResponse;
import com.sparta.moim.organization.presentation.dto.UpdateMemberInfoRequest;
import com.sparta.moim.organization.presentation.mapper.CommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations/{organizationTrackingId}/members")
@RequiredArgsConstructor
public class OrganizationMemberController {

    private final UpdateMemberInfoUseCase updateMemberInfoUseCase;
    private final GetOrganizationMemberListUseCase getOrganizationMemberListUseCase;
    private final CommandMapper commandMapper;
    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<ApiResponseData<String>> updateMemberInfo(
            @PathVariable String organizationTrackingId,
            @RequestBody @Valid UpdateMemberInfoRequest request) {

        updateMemberInfoUseCase.execute(organizationTrackingId, "68926367-c01f-4f88-8f10-4c9797b77f8e" ,commandMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    // 모든 멤버 조회
    @GetMapping
    public ResponseEntity<ApiResponseData<Pagination<GetOrganizationMemberResponse>>> getOrganizationMemberList(
            @PathVariable String organizationTrackingId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pagination<GetOrganizationMemberResponse> organizationMemberList = getOrganizationMemberListUseCase.execute(organizationTrackingId, page, size);
        return ResponseEntity.ok(ApiResponseData.success(organizationMemberList));
    }

}
