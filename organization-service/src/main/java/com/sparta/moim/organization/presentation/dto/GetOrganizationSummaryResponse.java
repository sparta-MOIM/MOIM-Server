package com.sparta.moim.organization.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrganizationSummaryResponse {
    private String organizationTrackingId;
    private String organizationName;
    private Integer memberCount;
}
