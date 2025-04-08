package com.sparta.moim.organization.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrganizationResponse {
    private String organizationUuid;
    private String organizationName;
    private String description;
    private Integer memberCount;
}
